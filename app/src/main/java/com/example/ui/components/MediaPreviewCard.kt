package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.FormatOption
import com.example.model.MediaMetadata
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassCardBackground
import com.example.ui.theme.GlassCardBackgroundActive
import com.example.ui.theme.GradientHeaderGlow
import com.example.ui.theme.GradientPrimary
import com.example.ui.theme.PrimaryPink
import com.example.ui.theme.SecondaryPurple
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun MediaPreviewCard(
    metadata: MediaMetadata?,
    isLoading: Boolean,
    onStartDownload: (FormatOption) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .background(GlassCardBackground, RoundedCornerShape(24.dp))
            .border(1.dp, GlassBorder, RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
    ) {
        // Subtle top gradient backdrop glow
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(GradientHeaderGlow)
        )

        if (isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CircularProgressIndicator(
                    color = PrimaryPink,
                    modifier = Modifier.size(36.dp)
                )
                Text(
                    text = "Analyzing media link...",
                    color = TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        } else if (metadata != null) {
            var selectedFormat by remember(metadata) {
                mutableStateOf(metadata.formatOptions.firstOrNull { it.isRecommended } ?: metadata.formatOptions.first())
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Video Thumbnail
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black.copy(alpha = 0.4f))
                        .border(1.dp, GlassBorder, RoundedCornerShape(16.dp))
                ) {
                    AsyncImage(
                        model = metadata.thumbnailUrl,
                        contentDescription = metadata.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Play icon overlay
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(48.dp)
                            .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                            .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(24.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Preview",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    // Duration badge
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp)
                            .background(Color.Black.copy(alpha = 0.8f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = metadata.durationStr,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Title & Channel info
                Text(
                    text = metadata.title,
                    color = TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${metadata.author} • ${metadata.viewsStr}",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Formats grid
                Text(
                    text = "SELECT FORMAT & QUALITY",
                    color = TextMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Grid of options
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    val rows = metadata.formatOptions.chunked(2)
                    for (row in rows) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            for (option in row) {
                                val isSelected = option.id == selectedFormat.id
                                FormatOptionChip(
                                    option = option,
                                    isSelected = isSelected,
                                    onSelect = { selectedFormat = option },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            if (row.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Download Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(GradientPrimary)
                        .clickable { onStartDownload(selectedFormat) }
                        .padding(vertical = 14.dp)
                        .testTag("start_download_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = "Download",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "DOWNLOAD ${selectedFormat.qualityStr.uppercase()} (${selectedFormat.sizeMB} MB)",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        } else {
            // Empty State
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "✨ Paste any video link to inspect quality & formats",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun FormatOptionChip(
    option: FormatOption,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor = if (isSelected) PrimaryPink.copy(alpha = 0.15f) else GlassCardBackground
    val borderColor = if (isSelected) PrimaryPink.copy(alpha = 0.5f) else GlassBorder
    val tagColor = if (isSelected) PrimaryPink else TextMuted

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(14.dp))
            .clickable { onSelect() }
            .padding(12.dp)
    ) {
        Column {
            Text(
                text = option.label.uppercase(),
                color = tagColor,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${option.qualityStr} • ${option.sizeMB}MB",
                color = TextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1
            )
        }
    }
}
