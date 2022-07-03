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
import androidx.navigation.NavHostController
import com.ognjenlazic.reddit.ui.elements.PostCard
import com.ognjenlazic.reddit.ui.elements.ReportCard
import com.ognjenlazic.reddit.viewModel.AllReportsScreenViewModel
import com.ognjenlazic.reddit.viewModel.PostsViewModel

@Composable
//@ExperimentalFoundationApi
fun AllReportsScreen(navController: NavHostController) {
    val viewModel = viewModel(modelClass = AllReportsScreenViewModel::class.java)
    viewModel.getAllReports()

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
            itemsIndexed(items = viewModel.reports) { index, report ->
                ReportCard(navController, report)
                if (index == viewModel.reports.size - 1){
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        }
    }
}