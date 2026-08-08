package com.example.ui.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassCardBackground
import com.example.ui.theme.PrimaryPink
import com.example.ui.theme.SecondaryPurple
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun SettingsScreen(
    onClearHistory: () -> Unit
) {
    var defaultQuality by remember { mutableStateOf("1080p Full HD") }
    var autoPasteClipboard by remember { mutableStateOf(true) }
    var highSpeedTurbo by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "APPLICATION SETTINGS",
            color = TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        // Section 1: Downloads
        Text(
            text = "DOWNLOAD PREFERENCES",
            color = TextMuted,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(GlassCardBackground, RoundedCornerShape(20.dp))
                .border(1.dp, GlassBorder, RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // Download Path
                Column {
                    Text(text = "Download Folder", color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = "/storage/emulated/0/Download/YouVideo", color = TextMuted, fontSize = 12.sp)
                }

                // Default Quality
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Preferred Quality", color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Text(text = defaultQuality, color = PrimaryPink, fontSize = 12.sp)
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(PrimaryPink.copy(alpha = 0.15f))
                            .border(1.dp, PrimaryPink.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                            .clickable {
                                defaultQuality = if (defaultQuality == "1080p Full HD") "4K Ultra HD" else "1080p Full HD"
                            }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("CHANGE", color = PrimaryPink, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                // Auto Paste
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Auto-Paste Clipboard Link", color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Detect copied media links automatically", color = TextMuted, fontSize = 12.sp)
                    }

                    Switch(
                        checked = autoPasteClipboard,
                        onCheckedChange = { autoPasteClipboard = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = PrimaryPink,
                            checkedTrackColor = PrimaryPink.copy(alpha = 0.3f)
                        )
                    )
                }

                // Turbo Download Engine
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Multi-Thread Turbo Speed", color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Accelerate download speed up to 8x", color = TextMuted, fontSize = 12.sp)
                    }

                    Switch(
                        checked = highSpeedTurbo,
                        onCheckedChange = { highSpeedTurbo = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = SecondaryPurple,
                            checkedTrackColor = SecondaryPurple.copy(alpha = 0.3f)
                        )
                    )
                }
            }
        }

        // Section 2: Storage & Data
        Text(
            text = "STORAGE & DATA",
            color = TextMuted,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(GlassCardBackground, RoundedCornerShape(20.dp))
                .border(1.dp, GlassBorder, RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Clear All Download Logs", color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = "CLEAR",
                        color = PrimaryPink,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onClearHistory() }
                    )
                }

                Text(
                    text = "YouVideo v1.0.0 • Glassmorphic Dark Theme Edition",
                    color = TextMuted,
                    fontSize = 11.sp
                )
            }
        }
    }
}
