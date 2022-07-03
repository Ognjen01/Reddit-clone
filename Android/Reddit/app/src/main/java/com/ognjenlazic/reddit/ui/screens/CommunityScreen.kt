package com.ognjenlazic.reddit.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ognjenlazic.reddit.ui.elements.CommunityInfoCard
import com.ognjenlazic.reddit.ui.elements.PostCard
import com.ognjenlazic.reddit.viewModel.CommunityScreenViewModel
import com.ognjenlazic.reddit.viewModel.PostsViewModel

@Composable
@ExperimentalFoundationApi
fun CommunityScreen(navController: NavController, communityId: Int) {
    val viewModel = viewModel(modelClass = CommunityScreenViewModel::class.java)
    viewModel.communityId = communityId
    viewModel.getCommunityInfo(communityId = communityId)
    viewModel.getAllCommunityPosts(communityId = communityId)
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

            itemsIndexed(items = viewModel.communityPosts + 1) { index, post ->
                if (index == 0) {
                    CommunityInfoCard(navController = navController, viewModel.community)
                } else {
                    PostCard(viewModel.communityPosts[index-1], navController)
                }

                if (index == viewModel.communityPosts.size){
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        }
    }
}
