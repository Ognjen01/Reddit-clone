package com.ognjenlazic.reddit.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Profile : BottomBarScreen(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )

    object NewPost : BottomBarScreen(
        route = "new_post",
        title = "Create",
        icon = Icons.Default.Add
    )

    object AllReports : BottomBarScreen(
        route = "All reports",
        title = "Report",
        icon = Icons.Default.Flag
    )
}
