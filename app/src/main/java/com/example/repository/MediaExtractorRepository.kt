package com.example.repository

import com.example.model.FormatOption
import com.example.model.MediaMetadata
import com.example.model.PlatformType
import kotlinx.coroutines.delay
import java.util.Locale

class MediaExtractorRepository {

    fun detectPlatform(url: String): PlatformType {
        val lower = url.lowercase(Locale.ROOT)
        return when {
            lower.contains("youtube.com") || lower.contains("youtu.be") -> PlatformType.YOUTUBE
            lower.contains("tiktok.com") -> PlatformType.TIKTOK
            lower.contains("instagram.com") || lower.contains("instagr.am") -> PlatformType.INSTAGRAM
            lower.contains("twitter.com") || lower.contains("x.com") -> PlatformType.TWITTER
            lower.contains("facebook.com") || lower.contains("fb.watch") -> PlatformType.FACEBOOK
            lower.contains("soundcloud.com") -> PlatformType.SOUNDCLOUD
            else -> PlatformType.GENERIC
        }
    }

    suspend fun extractMetadata(url: String): MediaMetadata {
        // Simulate real network metadata extraction delay
        delay(700)
        val platform = detectPlatform(url)

        return when (platform) {
            PlatformType.YOUTUBE -> {
                if (url.lowercase().contains("lofi") || url.isEmpty()) {
                    MediaMetadata(
                        url = url,
                        platform = PlatformType.YOUTUBE,
                        title = "Lofi Hip Hop Radio - Beats to Relax/Study to",
                        author = "Lofi Girl • 12K Watching",
                        viewsStr = "1.4M views",
                        durationStr = "04:12",
                        thumbnailUrl = "https://images.unsplash.com/photo-1611162617474-5b21e879e113?q=80&w=600",
                        formatOptions = listOf(
                            FormatOption("yt_1080p", "Full HD Video", "1080p", "VIDEO", 45.0, "mp4", isRecommended = true),
                            FormatOption("yt_4k", "Ultra HD Video", "4K 2160p", "VIDEO", 128.5, "mp4"),
                            FormatOption("yt_720p", "HD Video", "720p", "VIDEO", 22.4, "mp4"),
                            FormatOption("yt_mp3", "High Quality Audio", "MP3 • 320kbps", "AUDIO", 8.2, "mp3", isRecommended = true),
                            FormatOption("yt_m4a", "AAC Audio Track", "M4A • 192kbps", "AUDIO", 5.1, "m4a"),
                            FormatOption("yt_thumb", "Cover Art Image", "HD Wallpaper", "THUMBNAIL", 1.2, "jpg")
                        )
                    )
                } else {
                    MediaMetadata(
                        url = url,
                        platform = PlatformType.YOUTUBE,
                        title = "Chill Synthwave & Retro Future Cyberpunk Session",
                        author = "Neon Dreams • 450K views",
                        viewsStr = "450K views",
                        durationStr = "08:45",
                        thumbnailUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?q=80&w=600",
                        formatOptions = listOf(
                            FormatOption("yt2_1080p", "Full HD Video", "1080p", "VIDEO", 68.2, "mp4", isRecommended = true),
                            FormatOption("yt2_4k", "4K Ultra HD", "2160p 60fps", "VIDEO", 210.0, "mp4"),
                            FormatOption("yt2_mp3", "MP3 Audio", "320kbps", "AUDIO", 12.4, "mp3", isRecommended = true),
                            FormatOption("yt2_thumb", "Cover Art Image", "HD Wallpaper", "THUMBNAIL", 1.5, "jpg")
                        )
                    )
                }
            }

            PlatformType.TIKTOK -> {
                MediaMetadata(
                    url = url,
                    platform = PlatformType.TIKTOK,
                    title = "Viral Dance Trend & Cinematic Travel Transitions #fyp",
                    author = "@alex_creatives • 2.8M likes",
                    viewsStr = "8.9M views",
                    durationStr = "00:45",
                    thumbnailUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?q=80&w=600",
                    formatOptions = listOf(
                        FormatOption("tt_hd_nowatermark", "No Watermark HD", "1080p HD", "VIDEO", 14.2, "mp4", isRecommended = true),
                        FormatOption("tt_watermark", "Original Video", "720p", "VIDEO", 12.0, "mp4"),
                        FormatOption("tt_mp3", "Original Sound Audio", "MP3 • 320kbps", "AUDIO", 2.1, "mp3")
                    )
                )
            }

            PlatformType.INSTAGRAM -> {
                MediaMetadata(
                    url = url,
                    platform = PlatformType.INSTAGRAM,
                    title = "Minimalist Interior Design & Architectural Photography Reel",
                    author = "@design_aesthetic • 840K likes",
                    viewsStr = "1.2M views",
                    durationStr = "01:15",
                    thumbnailUrl = "https://images.unsplash.com/photo-1618221195710-dd6b41faaea6?q=80&w=600",
                    formatOptions = listOf(
                        FormatOption("ig_reel_hd", "Full HD Reel", "1080p", "VIDEO", 28.5, "mp4", isRecommended = true),
                        FormatOption("ig_audio", "Reel Background Audio", "MP3 • 320kbps", "AUDIO", 3.8, "mp3"),
                        FormatOption("ig_cover", "Reel Cover Photo", "1080x1920", "THUMBNAIL", 0.9, "jpg")
                    )
                )
            }

            PlatformType.TWITTER -> {
                MediaMetadata(
                    url = url,
                    platform = PlatformType.TWITTER,
                    title = "SpaceX Falcon Heavy Launch 4K Camera Tracking",
                    author = "@SpaceExplorer • 95K retweets",
                    viewsStr = "3.1M views",
                    durationStr = "02:10",
                    thumbnailUrl = "https://images.unsplash.com/photo-1517976487492-5750f3195933?q=80&w=600",
                    formatOptions = listOf(
                        FormatOption("tw_1080p", "HD Clip", "1080p", "VIDEO", 32.0, "mp4", isRecommended = true),
                        FormatOption("tw_720p", "Standard Clip", "720p", "VIDEO", 15.4, "mp4"),
                        FormatOption("tw_mp3", "Audio Only", "MP3 • 192kbps", "AUDIO", 3.1, "mp3")
                    )
                )
            }

            PlatformType.FACEBOOK -> {
                MediaMetadata(
                    url = url,
                    platform = PlatformType.FACEBOOK,
                    title = "Street Food Masterchef - 4K Culinary Highlights",
                    author = "Foodie World • 520K Shares",
                    viewsStr = "5.8M views",
                    durationStr = "05:30",
                    thumbnailUrl = "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?q=80&w=600",
                    formatOptions = listOf(
                        FormatOption("fb_hd", "FB Watch HD", "1080p", "VIDEO", 54.0, "mp4", isRecommended = true),
                        FormatOption("fb_sd", "FB Watch SD", "480p", "VIDEO", 18.2, "mp4"),
                        FormatOption("fb_mp3", "Audio Track", "MP3 • 256kbps", "AUDIO", 7.4, "mp3")
                    )
                )
            }

            PlatformType.SOUNDCLOUD -> {
                MediaMetadata(
                    url = url,
                    platform = PlatformType.SOUNDCLOUD,
                    title = "Deep Focus Ambient & Rain Soundscapes (Lossless)",
                    author = "Aura Soundscapes",
                    viewsStr = "280K plays",
                    durationStr = "12:00",
                    thumbnailUrl = "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?q=80&w=600",
                    formatOptions = listOf(
                        FormatOption("sc_lossless", "HQ Lossless MP3", "320kbps", "AUDIO", 18.5, "mp3", isRecommended = true),
                        FormatOption("sc_wav", "Uncompressed WAV", "24-bit 48kHz", "AUDIO", 65.0, "wav"),
                        FormatOption("sc_cover", "High-Res Artwork", "1400x1400", "THUMBNAIL", 1.8, "jpg")
                    )
                )
            }

            PlatformType.GENERIC -> {
                MediaMetadata(
                    url = url,
                    platform = PlatformType.GENERIC,
                    title = "Extracted Web Media Stream & High Definition Clip",
                    author = "Web Stream",
                    viewsStr = "Direct Link",
                    durationStr = "03:45",
                    thumbnailUrl = "https://images.unsplash.com/photo-1516321318423-f06f85e504b3?q=80&w=600",
                    formatOptions = listOf(
                        FormatOption("gen_hd", "Direct HD Stream", "1080p MP4", "VIDEO", 35.0, "mp4", isRecommended = true),
                        FormatOption("gen_audio", "Extracted Audio", "320kbps MP3", "AUDIO", 5.2, "mp3")
                    )
                )
            }
        }
    }
}
