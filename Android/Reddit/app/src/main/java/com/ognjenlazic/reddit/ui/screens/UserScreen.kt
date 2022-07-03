package com.ognjenlazic.reddit.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ognjenlazic.reddit.ui.elements.PostCard
import com.ognjenlazic.reddit.ui.elements.UserInfoCard
import com.ognjenlazic.reddit.viewModel.UserScreenViewModel


@Composable
@ExperimentalFoundationApi
fun UserScreen(navController: NavController, userId: Int) {
    val viewModel = viewModel(modelClass = UserScreenViewModel::class.java)
    viewModel.userId = userId
    viewModel.getUserInfo()
    viewModel.getUserPosts()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {

            itemsIndexed(items = viewModel.userPosts + 1) { index, post ->
                if (index == 0) {
                    UserInfoCard(navController = navController, viewModel.user)
                } else {
                    PostCard(viewModel.userPosts[index-1], navController)
                }
            }
        }
    }
}
