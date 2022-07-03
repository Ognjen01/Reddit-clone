package com.ognjenlazic.reddit.ui.elements


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Message
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.ognjenlazic.reddit.R
import com.ognjenlazic.reddit.model.Post
import com.ognjenlazic.reddit.model.Report
import com.ognjenlazic.reddit.model.ReportTypes
import com.ognjenlazic.reddit.network.RetrofitInstance
import com.ognjenlazic.reddit.repository.DataStorePreferenceRepository
import com.ognjenlazic.reddit.ui.theme.RedditGreen
import com.ognjenlazic.reddit.ui.theme.RedditWhite
import com.ognjenlazic.reddit.viewModel.PostCardViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@Composable
fun PostCard(post: Post, navController: NavController) {
    val avatar: Painter = painterResource(id = R.drawable.avatar_one)
    val reportReasons = ReportTypes.values()
    val openDialog = remember { mutableStateOf(false) }
    val openReportDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val dataStorePreferenceRepository: DataStorePreferenceRepository =
        DataStorePreferenceRepository.getInstance(context)
    val userInfo = dataStorePreferenceRepository.getUserInformation

    var corutineScope = rememberCoroutineScope()

    val viewModel = PostCardViewModel(post)
//    viewModel.getInformation()
    viewModel.getCurrentUserInfo(userInfo)
    viewModel.checkIsAlreadyReacted(viewModel.post)
    viewModel.checkIsUserBlockedByCommunity(viewModel.post)
//    viewModel.post = post

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    }
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {

                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .aspectRatio(avatar.intrinsicSize.width / avatar.intrinsicSize.height),
                    painter = avatar,
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
                Column(

                ) {
                    Text(
                        modifier = Modifier.clickable {
                            navController.navigate("community_screen/${viewModel.community.communityId}")
                        },
                        text = "r/${viewModel.community.name}",
                        color = Color.White,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        modifier = Modifier.clickable {
                            navController.navigate("user_screen/${viewModel.user.userId}")
                        },
                        text = "u/${viewModel.username}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.weight(1.0f))
                if (viewModel.currentUser.userId == post.userId) {
                    Icon(
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                navController.navigate("edit_post_screen/${viewModel.post.id}")
                            },
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Comment",
                        tint = Color.White
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "#${viewModel.flair}",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Justify
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "${post.title}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Justify
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "${post.text}",
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.clickable {
                        navController.navigate("single_post_screen/${viewModel.post.id}")
                    }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (post.imagePath != null) {
                Row() {
                    GlideImage(imageModel = post.imagePath,
                        loading = {
                            Box(modifier = Modifier.matchParentSize()) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        },
                        failure = {
                            Text(text = "image request failed.")
                        })
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .rotate(-90f)
                        .clickable {
                            viewModel.react("UPVOTE", viewModel.post)
                            viewModel.karma.number += 1
                        },
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Logo Icon",
                    tint = if (viewModel.reactedUpvote && viewModel.isAlreadyReacted) {
                        Color.Green
                    } else {
                        Color.White
                    },
                )
                Text(
                    text = "${viewModel.karma.number}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .rotate(-90f)
                        .clickable {
                            viewModel.react("DOWNVOTE", viewModel.post)
                            viewModel.karma.number -= 1
                        },

                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Logo Icon",
                    tint = if (viewModel.reactedDownvote && viewModel.isAlreadyReacted) {
                        Color.Red
                    } else {
                        Color.White
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            if(!viewModel.userBlockedByThisCommunity){
                                openDialog.value = true
                            }
                        },
                    imageVector = Icons.Outlined.Message,
                    contentDescription = "Comment",
                    tint = Color.White
                )
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            if(!viewModel.userBlockedByThisCommunity){
                                openReportDialog.value = true
                            }
                        },
                    imageVector = Icons.Outlined.Flag,
                    contentDescription = "Flag",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }

    // Comment dialog
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            backgroundColor = Color.Gray,
            title = {
                Text(text = "New comment")
            },
            text = {
                OutlinedTextField(
                    modifier = Modifier
                        .height(150.dp)
                        .width(280.dp),
                    value = viewModel.commentText,
                    label = { Text(text = "Text") },
                    onValueChange = { viewModel.commentText = it },
                )
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            viewModel.comment(viewModel.post)
                            Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show()
                            openDialog.value = false
                        }
                    ) {
                        Text("Comment")
                    }
                    Spacer(Modifier.width(10.dp))
                    Button(
                        onClick = { openDialog.value = false }
                    ) {
                        Text("Dismiss")
                    }
                }
            }
        )
    }

    // Report dialog variables
    var mExpanded by remember { mutableStateOf(false) }

    // Create a string value to store the selected city
    var mSelectedText by remember { mutableStateOf("") }

    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    // Report dialog
    if (openReportDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openReportDialog.value = false
            },
            backgroundColor = Color.Gray,
            text = {
                OutlinedTextField(
                    value = mSelectedText,
                    onValueChange = {
                        mSelectedText = it
                        viewModel.reportReason = it
                    },
                    modifier = Modifier
                        .width(280.dp)
                        .onGloballyPositioned { coordinates ->
                            // This value is used to assign to
                            mTextFieldSize = coordinates.size.toSize()
                        },
                    label = { Text("Report") },
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
                            mSelectedText = label.name
                            viewModel.reportReason = mSelectedText.toString()
                            viewModel.reportReason = label.name.toString()
                            mExpanded = false
                        }) {
                            Text(text = label.name)
                        }
                    }
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            println("mSelectedText: " + mSelectedText)

//                             TODO: Launch on creating report
                            var report = Report(false, null, post.id, mSelectedText, 0, "2022-01-12T17:14:14", 1) // Harcoded value

                            corutineScope.launch {
                                try {
                                    val reactionResponse = RetrofitInstance.report.createNew(report)
                                    Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show()
                                    openReportDialog.value = false
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Error!", Toast.LENGTH_LONG).show()
                                    openReportDialog.value = false
                                }
                            }

                        }
                    ) {
                        Text("Report")
                    }
                    Spacer(Modifier.width(10.dp))
                    Button(
                        onClick = { openReportDialog.value = false }
                    ) {
                        Text("Dismiss")
                    }
                }
            }
        )
    }

}