package com.ognjenlazic.reddit.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ognjenlazic.reddit.repository.DataStorePreferenceRepository
import com.ognjenlazic.reddit.repository.DataStoreViewModelFactory
import com.ognjenlazic.reddit.ui.elements.CommentCard
import com.ognjenlazic.reddit.ui.elements.CommunityInfoCard
import com.ognjenlazic.reddit.ui.elements.PostCard
import com.ognjenlazic.reddit.viewModel.CreateCommunityViewModel
import com.ognjenlazic.reddit.viewModel.LoginViewModel
import com.ognjenlazic.reddit.viewModel.SinglePostViewModel
import kotlinx.coroutines.flow.collect


@Composable
@ExperimentalFoundationApi
fun SinglePostScreen(
    navController: NavController, postId: Int
) {
    val viewModel = SinglePostViewModel(postId)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val dataStorePreferenceRepository: DataStorePreferenceRepository =
        DataStorePreferenceRepository.getInstance(context)
    val userInfo = dataStorePreferenceRepository.getUserInformation
    viewModel.getCurrentUserInfo(userInfo)
    viewModel.postId = postId
    viewModel.getPost()
    viewModel.getComments()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            itemsIndexed(items = viewModel.comments + 2) { index, comment ->
                if(index == 1){

                    var mExpanded by remember { mutableStateOf(false) }

                    var mSelectedText by remember { mutableStateOf("") }

                    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
                    val reportReasons = arrayOf("Popular", "Unpopular", "Newest", "Oldest")

                    val icon = if (mExpanded)
                        Icons.Filled.KeyboardArrowUp
                    else
                        Icons.Filled.KeyboardArrowDown

                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = mSelectedText,
                        onValueChange = {
                            mSelectedText = it
                        },
                        modifier = Modifier
                            .width(280.dp)
                            .onGloballyPositioned { coordinates ->
                                mTextFieldSize = coordinates.size.toSize()
                            },
                        label = { Text("Sort") },
                        trailingIcon = {
                            Icon(icon, "contentDescription",
                                Modifier.clickable { mExpanded = !mExpanded })
                        }
                    )
                    DropdownMenu(
                        expanded = mExpanded,
                        onDismissRequest = { mExpanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
                    ) {
                        reportReasons.forEach { label ->
                            DropdownMenuItem(onClick = {
                                mSelectedText = label
                                mExpanded = false
                                viewModel.selectedSortType = mSelectedText
                                viewModel.getSortedComments()

                            }) {
                                Text(text = label)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }
                if (index == 0) {
                    PostCard(post = viewModel.post, navController = navController)
                }else {
                    CommentCard(viewModel.comments[index - 1])
                    if (index == viewModel.comments.size) {
                        Spacer(modifier = Modifier.height(60.dp))
                    }
                }
            }
        }
    }
}