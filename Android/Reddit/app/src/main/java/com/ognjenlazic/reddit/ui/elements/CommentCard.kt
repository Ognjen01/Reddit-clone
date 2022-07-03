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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Message
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ognjenlazic.reddit.R
import com.ognjenlazic.reddit.model.ReportTypes
import com.ognjenlazic.reddit.navigation.Screen
import com.ognjenlazic.reddit.network.model.CommentResponse
import com.ognjenlazic.reddit.repository.DataStorePreferenceRepository
import com.ognjenlazic.reddit.viewModel.CommentCardViewModel
import com.ognjenlazic.reddit.viewModel.SinglePostViewModel
import org.w3c.dom.Comment


@Composable
fun CommentCard(comment: CommentResponse) {
    val viewModel = CommentCardViewModel()
    viewModel.comment = comment
    viewModel.getCommentUser()
    viewModel.getCommentKarma()
    viewModel.getCommentComments()

    val context = LocalContext.current
    val dataStorePreferenceRepository: DataStorePreferenceRepository =
        DataStorePreferenceRepository.getInstance(context)
    val userInfo = dataStorePreferenceRepository.getUserInformation
    viewModel.getCurrentUserInfo(userInfo)

    val avatar: Painter = painterResource(id = R.drawable.avatar_one)
    val openDialog = remember { mutableStateOf(false) }
    val openReportDialog = remember { mutableStateOf(false) }
    viewModel.checkIsUserBlockedByCommunity(comment)
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable {

                    },
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
                Text(
                    text = viewModel.user.username,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    }
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = comment.text,
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Justify
                )
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
                        .clickable {
                            viewModel.react("UPVOTE")
                        }
                        .rotate(-90f),
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Logo Icon",
                    tint = if (viewModel.reactedUpvote) {
                        Color.Green
                    } else {
                        Color.Black
                    }
                )
                Text(
                    text = viewModel.karma.number.toString(),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                )
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            viewModel.react("DOWNVOTE")
                        }
                        .rotate(-90f),
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Logo Icon",
                    tint = if (viewModel.reactedDownvote) {
                        Color.Red
                    } else {
                        Color.Black
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            openDialog.value = true
                        },
                    imageVector = Icons.Outlined.Message,
                    contentDescription = "Logo Icon",
                    tint = Color.Black
                )
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            openReportDialog.value = true
                        },
                    imageVector = Icons.Outlined.Flag,
                    contentDescription = "Logo Icon",
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            for (comment in viewModel.comments){
                CommentCard(comment = comment)
                Spacer(Modifier.height(10.dp))
            }
        }
    }

    val reportReasons = ReportTypes.values()

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
                            viewModel.replyToComment()
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
                            viewModel.reportComment(mSelectedText)
                            Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show()
                            openReportDialog.value = false
//                             TODO: Report
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