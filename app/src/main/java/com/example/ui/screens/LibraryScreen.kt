package com.example.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.DownloadEntity
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassCardBackground
import com.example.ui.theme.PrimaryPink
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LibraryScreen(
    downloads: List<DownloadEntity>,
    selectedFilter: String,
    searchQuery: String,
    onFilterChange: (String) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onToggleFavorite: (DownloadEntity) -> Unit,
    onDelete: (DownloadEntity) -> Unit,
    onClearAll: () -> Unit
) {
    val context = LocalContext.current
    var previewItem by remember { mutableStateOf<DownloadEntity?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Search & Clear Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "DOWNLOAD LIBRARY (${downloads.size})",
                color = TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            if (downloads.isNotEmpty()) {
                Text(
                    text = "CLEAR ALL",
                    color = PrimaryPink,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onClearAll() }
                )
            }
        }

        // Search Input
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(GlassCardBackground, RoundedCornerShape(16.dp))
                .border(1.dp, GlassBorder, RoundedCornerShape(16.dp))
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = TextMuted,
                    modifier = Modifier.size(18.dp)
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                ) {
                    if (searchQuery.isEmpty()) {
                        Text("Search downloads...", color = TextMuted, fontSize = 13.sp)
                    }
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChange,
                        textStyle = TextStyle(color = TextPrimary, fontSize = 13.sp),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Filter chips
        val filters = listOf("ALL", "VIDEO", "AUDIO", "FAVORITE")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filters) { filter ->
                val isSelected = filter == selectedFilter
                val chipBg = if (isSelected) PrimaryPink.copy(alpha = 0.2f) else GlassCardBackground
                val chipBorder = if (isSelected) PrimaryPink else GlassBorder
                val textColor = if (isSelected) PrimaryPink else TextSecondary

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(chipBg)
                        .border(1.dp, chipBorder, RoundedCornerShape(20.dp))
                        .clickable { onFilterChange(filter) }
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = filter,
                        color = textColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // List
        if (downloads.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "📂", fontSize = 48.sp)
                    Text(
                        text = "No saved downloads yet",
                        color = TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Downloaded media files will appear here for offline access",
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(downloads, key = { it.id }) { item ->
                    LibraryItemCard(
                        item = item,
                        onPreview = { previewItem = item },
                        onToggleFavorite = { onToggleFavorite(item) },
                        onShare = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, item.title)
                                putExtra(Intent.EXTRA_TEXT, "Check out ${item.title}: ${item.url}")
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share Media"))
                        },
                        onDelete = { onDelete(item) }
                    )
                }
            }
        }
    }

    // Preview Dialog
    previewItem?.let { item ->
        AlertDialog(
            onDismissRequest = { previewItem = null },
            confirmButton = {
                TextButton(onClick = { previewItem = null }) {
                    Text("Close", color = PrimaryPink)
                }
            },
            title = {
                Text(text = item.title, color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = item.thumbnailUrl,
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )

                    Text(
                        text = "${item.platform} • ${item.format} • ${item.fileSizeMB} MB",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )

                    Text(
                        text = "Saved at: ${item.filePath}",
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                }
            },
            containerColor = Color(0xFF181820)
        )
    }
}

@Composable
fun LibraryItemCard(
    item: DownloadEntity,
    onPreview: () -> Unit,
    onToggleFavorite: () -> Unit,
    onShare: () -> Unit,
    onDelete: () -> Unit
) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val dateStr = dateFormat.format(Date(item.downloadedAt))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(GlassCardBackground, RoundedCornerShape(18.dp))
            .border(1.dp, GlassBorder, RoundedCornerShape(18.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail with play preview
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onPreview() },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = item.thumbnailUrl,
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(Color.Black.copy(alpha = 0.6f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    color = TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${item.platform} • ${item.format}",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${item.fileSizeMB} MB • $dateStr",
                    color = TextMuted,
                    fontSize = 11.sp
                )
            }

            // Action icons
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onToggleFavorite() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (item.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (item.isFavorite) PrimaryPink else TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onShare() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = TextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onDelete() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
