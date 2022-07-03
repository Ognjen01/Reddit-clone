package com.ognjenlazic.reddit.ui.screens

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ognjenlazic.reddit.navigation.SetupBottomNavGraph
import com.ognjenlazic.reddit.navigation.SetupLogoutNavGraph
import com.ognjenlazic.reddit.navigation.SetupNavGraph

@Composable
fun Transition() {
    val context = LocalContext.current
    val navController = rememberNavController()
    SetupLogoutNavGraph(navController = navController)
}