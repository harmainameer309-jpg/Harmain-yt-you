package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ActiveDownload
import com.example.ui.components.ActiveDownloadCard
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary

@Composable
fun ActiveDownloadsScreen(
    downloads: List<ActiveDownload>,
    onTogglePause: (Long) -> Unit,
    onCancel: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "ACTIVE DOWNLOAD MANAGER (${downloads.size})",
            color = TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        if (downloads.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "⬇️", fontSize = 48.sp)
                    Text(
                        text = "No active downloads",
                        color = TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Start downloading videos or audio from the Home screen",
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
                items(downloads, key = { it.id }) { download ->
                    ActiveDownloadCard(
                        download = download,
                        onTogglePause = { onTogglePause(download.id) },
                        onCancel = { onCancel(download.id) }
                    )
                }
            }
        }
    }
}
