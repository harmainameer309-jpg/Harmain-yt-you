package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassCardBackground
import com.example.ui.theme.GradientPrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

data class RecommendedMedia(
    val title: String,
    val platform: String,
    val duration: String,
    val url: String,
    val thumbnailUrl: String
)

@Composable
fun BrowserScreen(
    onSelectUrl: (String) -> Unit
) {
    val recommendations = listOf(
        RecommendedMedia(
            title = "Lofi Hip Hop Radio - Beats to Relax/Study to",
            platform = "YouTube",
            duration = "04:12",
            url = "https://youtube.com/watch?v=lofi",
            thumbnailUrl = "https://images.unsplash.com/photo-1611162617474-5b21e879e113?q=80&w=600"
        ),
        RecommendedMedia(
            title = "Viral Dance Trend & Travel Reel",
            platform = "TikTok",
            duration = "00:45",
            url = "https://tiktok.com/@creator/video/123",
            thumbnailUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?q=80&w=600"
        ),
        RecommendedMedia(
            title = "Minimalist Architecture & Interior Reel",
            platform = "Instagram",
            duration = "01:15",
            url = "https://instagram.com/reel/C3x91",
            thumbnailUrl = "https://images.unsplash.com/photo-1618221195710-dd6b41faaea6?q=80&w=600"
        ),
        RecommendedMedia(
            title = "SpaceX Launch 4K Tracking Shot",
            platform = "Twitter/X",
            duration = "02:10",
            url = "https://x.com/space/status/987",
            thumbnailUrl = "https://images.unsplash.com/photo-1517976487492-5750f3195933?q=80&w=600"
        ),
        RecommendedMedia(
            title = "Deep Focus Ambient & Soundscapes",
            platform = "SoundCloud",
            duration = "12:00",
            url = "https://soundcloud.com/artist/track",
            thumbnailUrl = "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?q=80&w=600"
        ),
        RecommendedMedia(
            title = "Street Food Masterchef Highlights",
            platform = "Facebook",
            duration = "05:30",
            url = "https://facebook.com/watch/?v=456",
            thumbnailUrl = "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?q=80&w=600"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "MEDIA EXPLORE & DISCOVER",
            color = TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Tap any trending stream to extract available download formats",
            color = TextSecondary,
            fontSize = 13.sp
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(recommendations) { item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(GlassCardBackground)
                        .border(1.dp, GlassBorder, RoundedCornerShape(18.dp))
                        .clickable { onSelectUrl(item.url) }
                        .padding(10.dp)
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 10f)
                                .clip(RoundedCornerShape(12.dp))
                        ) {
                            AsyncImage(
                                model = item.thumbnailUrl,
                                contentDescription = item.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(6.dp)
                                    .background(Color.Black.copy(alpha = 0.8f), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = item.duration,
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = item.title,
                            color = TextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.platform,
                                color = TextMuted,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(GradientPrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Download,
                                    contentDescription = "Extract",
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
