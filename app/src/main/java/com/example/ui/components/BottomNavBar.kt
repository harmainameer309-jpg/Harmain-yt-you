package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkNavBackground
import com.example.ui.theme.PrimaryPink
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary

@Composable
fun BottomNavBar(
    selectedTab: Int,
    activeDownloadsCount: Int,
    onSelectTab: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkNavBackground)
            .border(width = 1.dp, color = Color.White.copy(alpha = 0.05f))
            .navigationBarsPadding()
            .padding(vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem(
                icon = Icons.Default.Home,
                label = "HOME",
                isSelected = selectedTab == 0,
                onClick = { onSelectTab(0) },
                testTag = "tab_home"
            )

            NavItem(
                icon = Icons.Default.Download,
                label = "ACTIVE",
                isSelected = selectedTab == 1,
                badgeCount = activeDownloadsCount,
                onClick = { onSelectTab(1) },
                testTag = "tab_active"
            )

            NavItem(
                icon = Icons.Default.Folder,
                label = "LIBRARY",
                isSelected = selectedTab == 2,
                onClick = { onSelectTab(2) },
                testTag = "tab_library"
            )

            NavItem(
                icon = Icons.Default.Search,
                label = "BROWSER",
                isSelected = selectedTab == 3,
                onClick = { onSelectTab(3) },
                testTag = "tab_browser"
            )
        }
    }
}

@Composable
private fun NavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    badgeCount: Int = 0,
    onClick: () -> Unit,
    testTag: String
) {
    val tint = if (isSelected) PrimaryPink else TextMuted

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .clickable { onClick() }
            .testTag(testTag)
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = tint,
                modifier = Modifier.size(24.dp)
            )

            if (badgeCount > 0) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(PrimaryPink, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$badgeCount",
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Text(
            text = label,
            color = tint,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )
    }
}
