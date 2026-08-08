package com.example.model

enum class PlatformType(
    val displayName: String,
    val iconEmoji: String,
    val colorHex: Long,
    val badgeText: String
) {
    YOUTUBE("YouTube", "🎬", 0xFFFF3366, "YouTube Detected"),
    TIKTOK("TikTok", "🎵", 0xFF00F2FE, "TikTok No-Watermark"),
    INSTAGRAM("Instagram", "📷", 0xFFE1306C, "Insta Reels 1080p"),
    TWITTER("Twitter / X", "🐦", 0xFF1DA1F2, "X / Twitter HD"),
    FACEBOOK("Facebook", "👤", 0xFF1877F2, "FB Watch HD"),
    SOUNDCLOUD("SoundCloud", "🎧", 0xFFFF5500, "SoundCloud Lossless"),
    GENERIC("Web Media", "🌐", 0xFF8A2BE2, "Media Stream")
}

data class FormatOption(
    val id: String,
    val label: String,
    val qualityStr: String,
    val fileType: String, // "VIDEO", "AUDIO", "THUMBNAIL"
    val sizeMB: Double,
    val extension: String,
    val isRecommended: Boolean = false
)

data class MediaMetadata(
    val url: String,
    val platform: PlatformType,
    val title: String,
    val author: String,
    val viewsStr: String,
    val durationStr: String,
    val thumbnailUrl: String,
    val formatOptions: List<FormatOption>
)

enum class DownloadStatus {
    DOWNLOADING,
    PAUSED,
    COMPLETED,
    FAILED
}

data class ActiveDownload(
    val id: Long = System.currentTimeMillis(),
    val url: String,
    val title: String,
    val author: String,
    val platform: PlatformType,
    val formatLabel: String,
    val fileType: String,
    val extension: String,
    val thumbnailUrl: String,
    val totalSizeMB: Double,
    val downloadedMB: Double = 0.0,
    val progressPercent: Float = 0f,
    val speedMBs: Double = 0.0,
    val etaSeconds: Int = 0,
    val status: DownloadStatus = DownloadStatus.DOWNLOADING
)
