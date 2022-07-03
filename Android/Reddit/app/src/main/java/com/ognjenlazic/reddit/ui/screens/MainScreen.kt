package com.ognjenlazic.reddit.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ognjenlazic.reddit.navigation.SetupBottomNavGraph
import com.ognjenlazic.reddit.repository.DataStorePreferenceRepository
import com.ognjenlazic.reddit.viewModel.MainScreenViewModel

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
                BottomBar(navController = navController)
        }
    ) {
        SetupBottomNavGraph(navController = navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.NewPost,
        BottomBarScreen.Profile,
    )
    // TODO: Moguće greške ovdje jer je bio onaj ViewModel a sad sam promjenio - provjeriti
    val viewModel = viewModel(modelClass = MainScreenViewModel::class.java)
    val context = LocalContext.current
    val dataStorePreferenceRepository: DataStorePreferenceRepository =
        DataStorePreferenceRepository.getInstance(context)
    val userInfo = dataStorePreferenceRepository.getUserInformation
    Log.d("SharedPref", "Main screen -> " + userInfo.toString())
    viewModel.getCurrentUserInfo(userInfo)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        //modifier = Modifier.background(RedditOrange)
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }

        if (viewModel.user.userType == "ADMIN") {
            AddItem(
                screen = BottomBarScreen.AllReports,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        modifier = Modifier.background(Color.DarkGray),
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }

    )
}











