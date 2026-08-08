package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassCardBackground
import com.example.ui.theme.GradientPrimary
import com.example.ui.theme.TextPrimary

@Composable
fun HeaderSection(
    onOpenSettings: () -> Unit,
    onOpenLibrary: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // App Branding
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Gradient square logo with rotated diamond
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(GradientPrimary, shape = RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .rotate(45f)
                        .background(Color.White, shape = RoundedCornerShape(2.dp))
                )
            }

            Text(
                text = "YouVideo",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                letterSpacing = (-0.5).sp
            )
        }

        // Action Buttons
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(GlassCardBackground, CircleShape)
                    .border(1.dp, GlassBorder, CircleShape)
                    .clickable { onOpenLibrary() }
                    .testTag("header_library_button"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Folder,
                    contentDescription = "Library",
                    tint = TextPrimary.copy(alpha = 0.8f),
                    modifier = Modifier.size(18.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(GlassCardBackground, CircleShape)
                    .border(1.dp, GlassBorder, CircleShape)
                    .clickable { onOpenSettings() }
                    .testTag("header_settings_button"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = TextPrimary.copy(alpha = 0.8f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
