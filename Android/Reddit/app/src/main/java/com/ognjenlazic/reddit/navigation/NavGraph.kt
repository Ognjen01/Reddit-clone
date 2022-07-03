package com.ognjenlazic.reddit.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ognjenlazic.reddit.ui.screens.*

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        /*navController = navController,
        startDestination = BottomBarScreen.Home.route*/
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // Navigacija za Splash Screen
        composable(route = Screen.Splash.route) {
            AnimatedSplashScreen(navController = navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.Home.route) {
            MainScreen()
        }
        composable(route = Screen.Register.route) {
            RegistrationScreen(navController = navController)
        }
    }
}

@Composable
fun SetupLogoutNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.Home.route) {
            MainScreen()
        }
        composable(route = Screen.Register.route) {
            RegistrationScreen(navController = navController)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SetupBottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        // Navigacija za glavni meni u aplikaciji
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(navHostController = navController)
        }

        composable(route = BottomBarScreen.NewPost.route) {
            CreateScreen(navController = navController)
        }

        composable(route = BottomBarScreen.AllReports.route) {
            AllReportsScreen(navController = navController)
        }

        composable(route = Screen.CreatePostScreen.route) {
            CreatePostScreen(navController = navController)
        }

        composable(route = "community_screen/{communityId}",
            arguments = listOf(navArgument("communityId") { type = NavType.IntType }
            )) { backStackEntry ->
                backStackEntry?.arguments?.getInt("communityId")?.let { communityId ->
                    CommunityScreen(navController = navController, communityId)
                }
        }

        composable(route = "edit_community_screen/{communityId}",
            arguments = listOf(navArgument("communityId") { type = NavType.IntType }
            )) { backStackEntry ->
            backStackEntry?.arguments?.getInt("communityId")?.let { communityId ->
                EditCommunityScreen(navController = navController, communityId)
            }
        }

        composable(route = "user_screen/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType }
            )) { backStackEntry ->
            backStackEntry?.arguments?.getInt("userId")?.let { userId ->
                UserScreen(navController = navController, userId)
            }
        }

        composable(route = "single_post_screen/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.IntType }
            )) { backStackEntry ->
            backStackEntry?.arguments?.getInt("postId")?.let { postId ->
                SinglePostScreen(navController = navController, postId)
            }
        }

        composable(route = "edit_post_screen/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.IntType }
            )) { backStackEntry ->
            backStackEntry?.arguments?.getInt("postId")?.let { postId ->
                EditPostScreen(navController = navController, postId)
            }
        }

        composable(route = Screen.CreateCommnity.route) {
            CreateCommunity(navController = navController)
        }

        composable(route = "reset_password") {
            ResetPasswordScreen(navController = navController)
        }

        composable(route = "edit_curren_user_profile") {
            EditProfileScreen(navController = navController)
        }

        composable(route = "transition") {
            Transition()
        }

        composable(route = Screen.Settings.route) {
            Settings()
        }

        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(route = Screen.Home.route) {
            MainScreen()
        }
        composable(route = Screen.Register.route) {
            RegistrationScreen(navController = navController)
        }
    }
}

