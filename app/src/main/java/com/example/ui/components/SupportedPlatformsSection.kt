package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PlatformType
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassCardBackground
import com.example.ui.theme.SecondaryPurple
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun SupportedPlatformsSection(
    onSelectSamplePlatform: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SUPPORTED PLATFORMS",
                color = TextMuted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )

            Text(
                text = "EXPLORE ALL",
                color = SecondaryPurple,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp,
                modifier = Modifier.clickable { onSelectSamplePlatform("https://youtube.com/watch?v=lofi") }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        val platforms = listOf(
            PlatformType.YOUTUBE to "https://youtube.com/watch?v=lofi",
            PlatformType.TIKTOK to "https://tiktok.com/@creator/video/123",
            PlatformType.INSTAGRAM to "https://instagram.com/reel/C3x91",
            PlatformType.TWITTER to "https://x.com/space/status/987",
            PlatformType.FACEBOOK to "https://facebook.com/watch/?v=456",
            PlatformType.SOUNDCLOUD to "https://soundcloud.com/artist/track"
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(platforms) { (platform, sampleUrl) ->
                val platformColor = Color(platform.colorHex)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.clickable { onSelectSamplePlatform(sampleUrl) }
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(platformColor.copy(alpha = 0.15f))
                            .border(1.dp, platformColor.copy(alpha = 0.4f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = platform.iconEmoji,
                            fontSize = 22.sp
                        )
                    }

                    Text(
                        text = platform.displayName,
                        color = TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
