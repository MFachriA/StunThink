package com.projectAnya.stunthink.domain.model.home

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon_active: ImageVector,
    val icon_inactive: ImageVector
)