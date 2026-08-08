package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.DownloadEntity
import com.example.model.ActiveDownload
import com.example.model.DownloadStatus
import com.example.model.FormatOption
import com.example.model.MediaMetadata
import com.example.model.PlatformType
import com.example.repository.MediaExtractorRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class YouVideoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MediaExtractorRepository()
    private val downloadDao = AppDatabase.getDatabase(application).downloadDao()

    val urlInput = MutableStateFlow("https://youtube.com/watch?v=dQw4w9WgXcQ")
    val detectedPlatform = MutableStateFlow(PlatformType.YOUTUBE)

    val isLoadingMetadata = MutableStateFlow(false)
    val extractedMetadata = MutableStateFlow<MediaMetadata?>(null)

    val selectedTab = MutableStateFlow(0) // 0: Home, 1: Active, 2: Library, 3: Browser, 4: Settings

    val activeDownloads = MutableStateFlow<List<ActiveDownload>>(emptyList())
    private val downloadJobs = mutableMapOf<Long, Job>()

    val toastMessage = MutableStateFlow<String?>(null)

    // Library filtering
    val libraryFilter = MutableStateFlow("ALL") // ALL, VIDEO, AUDIO, FAVORITE
    val searchQuery = MutableStateFlow("")

    val downloadHistory: StateFlow<List<DownloadEntity>> = combine(
        downloadDao.getAllDownloads(),
        libraryFilter,
        searchQuery
    ) { history, filter, query ->
        history.filter { item ->
            val matchesFilter = when (filter) {
                "VIDEO" -> item.fileType == "VIDEO"
                "AUDIO" -> item.fileType == "AUDIO"
                "FAVORITE" -> item.isFavorite
                else -> true
            }
            val matchesQuery = query.isEmpty() ||
                    item.title.contains(query, ignoreCase = true) ||
                    item.author.contains(query, ignoreCase = true) ||
                    item.platform.contains(query, ignoreCase = true)

            matchesFilter && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        // Auto-extract initial demo link
        onUrlChanged(urlInput.value)
        extractMedia()
    }

    fun onUrlChanged(newUrl: String) {
        urlInput.value = newUrl
        detectedPlatform.value = repository.detectPlatform(newUrl)
    }

    fun clearUrl() {
        urlInput.value = ""
        detectedPlatform.value = PlatformType.GENERIC
        extractedMetadata.value = null
    }

    fun extractMedia() {
        val url = urlInput.value.trim()
        if (url.isEmpty()) {
            showToast("Please enter or paste a valid URL")
            return
        }

        viewModelScope.launch {
            isLoadingMetadata.value = true
            try {
                val metadata = repository.extractMetadata(url)
                extractedMetadata.value = metadata
                detectedPlatform.value = metadata.platform
            } catch (e: Exception) {
                showToast("Failed to fetch media details")
            } finally {
                isLoadingMetadata.value = false
            }
        }
    }

    fun startDownload(option: FormatOption) {
        val metadata = extractedMetadata.value ?: return
        val downloadId = System.currentTimeMillis()

        val newDownload = ActiveDownload(
            id = downloadId,
            url = metadata.url,
            title = metadata.title,
            author = metadata.author,
            platform = metadata.platform,
            formatLabel = option.label + " (" + option.qualityStr + ")",
            fileType = option.fileType,
            extension = option.extension,
            thumbnailUrl = metadata.thumbnailUrl,
            totalSizeMB = option.sizeMB,
            downloadedMB = 0.0,
            progressPercent = 0f,
            speedMBs = 4.2,
            etaSeconds = (option.sizeMB / 4.2).toInt().coerceAtLeast(1),
            status = DownloadStatus.DOWNLOADING
        )

        activeDownloads.value = listOf(newDownload) + activeDownloads.value
        showToast("Starting download: ${metadata.title}")

        // Launch simulated background download job
        val job = viewModelScope.launch {
            simulateDownload(downloadId, option.sizeMB, metadata)
        }
        downloadJobs[downloadId] = job
    }

    private suspend fun simulateDownload(downloadId: Long, totalMB: Double, metadata: MediaMetadata) {
        var currentMB = 0.0
        val stepMs = 200L

        while (currentMB < totalMB) {
            val currentList = activeDownloads.value
            val item = currentList.find { it.id == downloadId } ?: break

            if (item.status == DownloadStatus.PAUSED) {
                delay(300)
                continue
            }
            if (item.status == DownloadStatus.FAILED) break

            val speed = (3.5 + Math.random() * 2.5) // 3.5 to 6.0 MB/s
            val increment = speed * (stepMs / 1000.0)
            currentMB = (currentMB + increment).coerceAtMost(totalMB)

            val percent = ((currentMB / totalMB) * 100).toFloat().coerceIn(0f, 100f)
            val remainingMB = totalMB - currentMB
            val eta = if (speed > 0) (remainingMB / speed).toInt() else 0

            updateActiveDownload(downloadId) { old ->
                old.copy(
                    downloadedMB = currentMB,
                    progressPercent = percent,
                    speedMBs = speed,
                    etaSeconds = eta
                )
            }

            delay(stepMs)
        }

        // Completed!
        updateActiveDownload(downloadId) { old ->
            old.copy(
                downloadedMB = totalMB,
                progressPercent = 100f,
                speedMBs = 0.0,
                etaSeconds = 0,
                status = DownloadStatus.COMPLETED
            )
        }

        // Save to Database
        val activeItem = activeDownloads.value.find { it.id == downloadId }
        if (activeItem != null) {
            val entity = DownloadEntity(
                title = activeItem.title,
                author = activeItem.author,
                platform = activeItem.platform.displayName,
                url = activeItem.url,
                format = activeItem.formatLabel,
                fileType = activeItem.fileType,
                fileSizeMB = activeItem.totalSizeMB,
                durationStr = metadata.durationStr,
                thumbnailUrl = activeItem.thumbnailUrl,
                filePath = "/storage/emulated/0/Download/YouVideo/${activeItem.title.take(20)}.${activeItem.extension}"
            )
            downloadDao.insertDownload(entity)
            showToast("Saved to Library: ${activeItem.title}")
        }
    }

    fun togglePauseDownload(id: Long) {
        updateActiveDownload(id) { old ->
            val newStatus = if (old.status == DownloadStatus.DOWNLOADING) DownloadStatus.PAUSED else DownloadStatus.DOWNLOADING
            old.copy(status = newStatus)
        }
    }

    fun cancelDownload(id: Long) {
        downloadJobs[id]?.cancel()
        downloadJobs.remove(id)
        activeDownloads.value = activeDownloads.value.filterNot { it.id == id }
        showToast("Download cancelled")
    }

    private fun updateActiveDownload(id: Long, transform: (ActiveDownload) -> ActiveDownload) {
        activeDownloads.value = activeDownloads.value.map { item ->
            if (item.id == id) transform(item) else item
        }
    }

    fun toggleFavorite(item: DownloadEntity) {
        viewModelScope.launch {
            downloadDao.updateDownload(item.copy(isFavorite = !item.isFavorite))
        }
    }

    fun deleteFromHistory(item: DownloadEntity) {
        viewModelScope.launch {
            downloadDao.deleteDownload(item)
            showToast("Deleted ${item.title}")
        }
    }

    fun clearAllHistory() {
        viewModelScope.launch {
            downloadDao.clearAll()
            showToast("Download history cleared")
        }
    }

    fun showToast(msg: String) {
        toastMessage.value = msg
    }

    fun dismissToast() {
        toastMessage.value = null
    }
}
