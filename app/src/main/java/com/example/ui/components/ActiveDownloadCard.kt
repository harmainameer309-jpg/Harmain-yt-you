package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.ActiveDownload
import com.example.model.DownloadStatus
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassCardBackground
import com.example.ui.theme.GradientPrimary
import com.example.ui.theme.PrimaryPink
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun ActiveDownloadCard(
    download: ActiveDownload,
    onTogglePause: () -> Unit,
    onCancel: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(GlassCardBackground, RoundedCornerShape(20.dp))
            .border(1.dp, GlassBorder, RoundedCornerShape(20.dp))
            .padding(14.dp)
            .testTag("active_download_card_${download.id}")
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            // Header with thumbnail, title & action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = download.thumbnailUrl,
                    contentDescription = download.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black.copy(alpha = 0.3f))
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = download.title,
                        color = TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "${download.formatLabel} • ${download.platform.displayName}",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }

                // Pause / Resume button
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.White.copy(alpha = 0.1f), CircleShape)
                        .clickable { onTogglePause() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (download.status == DownloadStatus.PAUSED) Icons.Default.PlayArrow else Icons.Default.Pause,
                        contentDescription = "Pause or Resume",
                        tint = TextPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }

                // Cancel button
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.White.copy(alpha = 0.1f), CircleShape)
                        .clickable { onCancel() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cancel",
                        tint = TextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Stats row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val statusText = when (download.status) {
                    DownloadStatus.DOWNLOADING -> "Downloading... ${download.progressPercent.toInt()}%"
                    DownloadStatus.PAUSED -> "Paused (${download.progressPercent.toInt()}%)"
                    DownloadStatus.COMPLETED -> "Completed 100%"
                    DownloadStatus.FAILED -> "Failed"
                }

                Text(
                    text = statusText,
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = if (download.status == DownloadStatus.DOWNLOADING) "%.1f MB/s".format(download.speedMBs) else "",
                    color = PrimaryPink,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Animated progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.1f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction = (download.progressPercent / 100f).coerceIn(0.01f, 1f))
                        .clip(CircleShape)
                        .background(GradientPrimary)
                )
            }

            // Footer info: Size & ETA
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "%.1fMB / %.1fMB".format(download.downloadedMB, download.totalSizeMB),
                    color = TextMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )

                if (download.status == DownloadStatus.DOWNLOADING && download.etaSeconds > 0) {
                    Text(
                        text = "ETA: ${download.etaSeconds}s",
                        color = PrimaryPink,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
