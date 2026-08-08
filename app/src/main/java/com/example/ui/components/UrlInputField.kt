package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PlatformType
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassCardBackground
import com.example.ui.theme.GradientPrimary
import com.example.ui.theme.PrimaryPink
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun UrlInputField(
    urlValue: String,
    detectedPlatform: PlatformType,
    onUrlChange: (String) -> Unit,
    onClear: () -> Unit,
    onExtract: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Input bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(GlassCardBackground, RoundedCornerShape(20.dp))
                .border(1.dp, GlassBorder, RoundedCornerShape(20.dp))
                .padding(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Text input
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp)
                ) {
                    if (urlValue.isEmpty()) {
                        Text(
                            text = "Paste video or audio link here...",
                            color = TextMuted,
                            fontSize = 14.sp
                        )
                    }

                    BasicTextField(
                        value = urlValue,
                        onValueChange = onUrlChange,
                        textStyle = TextStyle(
                            color = TextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                        keyboardActions = KeyboardActions(onGo = { onExtract() }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("url_text_input")
                    )
                }

                // Clear button if not empty
                if (urlValue.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .clickable { onClear() }
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = TextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // PASTE or EXTRACT button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(GradientPrimary)
                        .clickable {
                            if (urlValue.isEmpty()) {
                                val clipData = clipboardManager.getText()
                                if (!clipData.isNullOrEmpty()) {
                                    onUrlChange(clipData.text)
                                    onExtract()
                                }
                            } else {
                                onExtract()
                            }
                        }
                        .padding(horizontal = 18.dp, vertical = 10.dp)
                        .testTag("paste_extract_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        if (urlValue.isEmpty()) {
                            Icon(
                                imageVector = Icons.Default.ContentPaste,
                                contentDescription = "Paste",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "PASTE",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        } else {
                            Text(
                                text = "FETCH",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }
            }
        }

        // Platform badges row
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .background(PrimaryPink.copy(alpha = 0.15f), RoundedCornerShape(20.dp))
                        .border(1.dp, PrimaryPink.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${detectedPlatform.iconEmoji} ${detectedPlatform.badgeText}",
                        color = PrimaryPink,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .background(GlassCardBackground, RoundedCornerShape(20.dp))
                        .border(1.dp, GlassBorder, RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "4K & 1080p Support",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .background(GlassCardBackground, RoundedCornerShape(20.dp))
                        .border(1.dp, GlassBorder, RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "MP3 320kbps Audio",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
