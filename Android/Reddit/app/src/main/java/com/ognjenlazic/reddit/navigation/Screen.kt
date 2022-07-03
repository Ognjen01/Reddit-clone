package com.ognjenlazic.reddit.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Home : Screen("home_screen")
    object CommunityScreen : Screen("community_screen")
    object SinglePostScreen : Screen("single_post_screen")
    object Settings : Screen("settings")
    object SearchScreen : Screen("search_screen")
    object CreateCommnity : Screen("create_community")
    object CreateScreen : Screen("create_screen")
    object CreatePostScreen : Screen("create_post_screen")
    object AllReportsScreen : Screen("all_reports_screen")
    object Login : Screen("login")
    object Register : Screen("register")

}
