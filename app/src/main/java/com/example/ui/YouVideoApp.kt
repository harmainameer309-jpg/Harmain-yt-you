package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.ActiveDownloadCard
import com.example.ui.components.BottomNavBar
import com.example.ui.components.HeaderSection
import com.example.ui.components.MediaPreviewCard
import com.example.ui.components.SupportedPlatformsSection
import com.example.ui.components.UrlInputField
import com.example.ui.screens.ActiveDownloadsScreen
import com.example.ui.screens.BrowserScreen
import com.example.ui.screens.LibraryScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.PrimaryPink
import com.example.ui.theme.TextPrimary

@Composable
fun YouVideoApp(
    viewModel: YouVideoViewModel = viewModel()
) {
    val urlInput by viewModel.urlInput.collectAsState()
    val detectedPlatform by viewModel.detectedPlatform.collectAsState()
    val isLoadingMetadata by viewModel.isLoadingMetadata.collectAsState()
    val extractedMetadata by viewModel.extractedMetadata.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val activeDownloads by viewModel.activeDownloads.collectAsState()
    val downloadHistory by viewModel.downloadHistory.collectAsState()
    val libraryFilter by viewModel.libraryFilter.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val toastMsg by viewModel.toastMessage.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(toastMsg) {
        toastMsg?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.dismissToast()
        }
    }

    Scaffold(
        containerColor = DarkBackground,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(Color(0xFF22222E), RoundedCornerShape(12.dp))
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = data.visuals.message,
                            color = TextPrimary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavBar(
                selectedTab = selectedTab,
                activeDownloadsCount = activeDownloads.size,
                onSelectTab = { tab -> viewModel.selectedTab.value = tab }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding()
        ) {
            // Header
            HeaderSection(
                onOpenSettings = { viewModel.selectedTab.value = 4 },
                onOpenLibrary = { viewModel.selectedTab.value = 2 }
            )

            // Content per tab
            when (selectedTab) {
                0 -> { // HOME TAB
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        // Input section
                        UrlInputField(
                            urlValue = urlInput,
                            detectedPlatform = detectedPlatform,
                            onUrlChange = { viewModel.onUrlChanged(it) },
                            onClear = { viewModel.clearUrl() },
                            onExtract = { viewModel.extractMedia() }
                        )

                        // Extracted Media Preview Card
                        MediaPreviewCard(
                            metadata = extractedMetadata,
                            isLoading = isLoadingMetadata,
                            onStartDownload = { option -> viewModel.startDownload(option) }
                        )

                        // Active downloads preview if any
                        if (activeDownloads.isNotEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "ACTIVE DOWNLOAD IN PROGRESS",
                                    color = PrimaryPink,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )

                                val firstActive = activeDownloads.first()
                                ActiveDownloadCard(
                                    download = firstActive,
                                    onTogglePause = { viewModel.togglePauseDownload(firstActive.id) },
                                    onCancel = { viewModel.cancelDownload(firstActive.id) }
                                )
                            }
                        }

                        // Supported Platforms Explorer Grid
                        SupportedPlatformsSection(
                            onSelectSamplePlatform = { sampleUrl ->
                                viewModel.onUrlChanged(sampleUrl)
                                viewModel.extractMedia()
                            }
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                1 -> { // ACTIVE DOWNLOADS
                    ActiveDownloadsScreen(
                        downloads = activeDownloads,
                        onTogglePause = { id -> viewModel.togglePauseDownload(id) },
                        onCancel = { id -> viewModel.cancelDownload(id) }
                    )
                }

                2 -> { // LIBRARY
                    LibraryScreen(
                        downloads = downloadHistory,
                        selectedFilter = libraryFilter,
                        searchQuery = searchQuery,
                        onFilterChange = { viewModel.libraryFilter.value = it },
                        onSearchQueryChange = { viewModel.searchQuery.value = it },
                        onToggleFavorite = { viewModel.toggleFavorite(it) },
                        onDelete = { viewModel.deleteFromHistory(it) },
                        onClearAll = { viewModel.clearAllHistory() }
                    )
                }

                3 -> { // BROWSER
                    BrowserScreen(
                        onSelectUrl = { url ->
                            viewModel.onUrlChanged(url)
                            viewModel.selectedTab.value = 0
                            viewModel.extractMedia()
                        }
                    )
                }

                4 -> { // SETTINGS
                    SettingsScreen(
                        onClearHistory = { viewModel.clearAllHistory() }
                    )
                }
            }
        }
    }
}
