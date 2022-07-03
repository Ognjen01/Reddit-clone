package com.ognjenlazic.reddit.ui.elements

import android.graphics.Paint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ognjenlazic.reddit.R
import com.ognjenlazic.reddit.model.Community
import com.ognjenlazic.reddit.navigation.Screen
import com.ognjenlazic.reddit.repository.DataStorePreferenceRepository
import com.ognjenlazic.reddit.ui.theme.RedditGreen
import com.ognjenlazic.reddit.ui.theme.RedditRed
import com.ognjenlazic.reddit.ui.theme.RedditWhite
import com.ognjenlazic.reddit.viewModel.CommunityCardViewModel
import com.ognjenlazic.reddit.viewModel.SinglePostViewModel

@Composable
fun CommunityInfoCard(navController: NavController, community: Community) {

    val viewModel = viewModel(modelClass = CommunityCardViewModel::class.java)
    viewModel.communty = community
    val context = LocalContext.current
    val dataStorePreferenceRepository: DataStorePreferenceRepository =
        DataStorePreferenceRepository.getInstance(context)
    val userInfo = dataStorePreferenceRepository.getUserInformation
    viewModel.getCurrentUserInfo(userInfo)
    viewModel.getCommunityFlairs()
    viewModel.getAllUsers()
    viewModel.checkIsUserPartOfCommunity()

    val openReportDialog = remember { mutableStateOf(false) }
    val openBlockUserDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.clickable { println("Kliknuli ste karticu") },
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(Modifier.background(Color.Gray)) {
            Row(
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Column() {
                    Text(
                        text = community.name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
            Column(
                Modifier
                    .background(Color.Gray)
                    .padding(10.dp)
            ) {
                Text(
                    text = "Flairs:",
                    color = Color.Black,
                )
                for (flair in viewModel.flairList) {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = "#${flair.name}",
                        color = Color.Black,
                    )
                }
            }
            Column(
                Modifier
                    .background(Color.Gray)
                    .padding(10.dp)
            ) {
                Text(
                    text = "Rules:",
                    color = Color.White,
                )
                for (rule in community.rules!!) {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = rule.description,
                        color = Color.White,
                    )
                }
            }
            Row(
                Modifier
                    .padding(10.dp)
                    .background(Color.Gray)
            ) {
                Button(
                    onClick = {
                        viewModel.joinLeftCommunity()
                    },
                    colors = (if (viewModel.isUserPartOfCommunity) {
                        ButtonDefaults.buttonColors(backgroundColor = RedditRed)
                    } else {
                        ButtonDefaults.buttonColors(backgroundColor = RedditGreen)
                    })
//                    ButtonDefaults.buttonColors(backgroundColor = RedditWhite)
                ) {
                    Text(
                        text = (if (viewModel.isUserPartOfCommunity) {
                            "Left"
                        } else {
                            "Join"
                        }) ,
                        color = Color.Black,

                        )
                }
                Spacer(modifier = Modifier.width(10.dp))
                if (viewModel.user.userType == "ADMIN") {
                    Button(
                        onClick = {
                            openReportDialog.value = true
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = RedditWhite)
                    ) {
                        Text(
                            text = "Suspend",
                            color = Color.Black,

                            )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
                if (viewModel.user.userId == community.moderatorId) {
                    Button(
                        onClick = {
                                  navController.navigate("edit_community_screen/" + community.communityId)
                                  },
                        colors = ButtonDefaults.buttonColors(backgroundColor = RedditWhite)
                    ) {
                        Text(
                            text = "Edit",
                            color = Color.Black,

                            )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = {
                            openBlockUserDialog.value = true
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = RedditWhite)
                    ) {
                        Text(
                            text = "BLock user",
                            color = Color.Black,

                            )
                    }
                }
            }
        }
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
                    },
                    modifier = Modifier
                        .width(280.dp),
                    label = { Text("Reason") },
                )
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            println("mSelectedText: " + mSelectedText)
                            viewModel.suspendCommunity(mSelectedText).also {
                                navController.navigate(Screen.Home.route)
                                Toast.makeText(context, "Community suspended!", Toast.LENGTH_LONG).show()

                            }
                        }
                    ) {
                        Text("Suspend")
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


    // Block user dialog variables
    var mExpandedBlockUser by remember { mutableStateOf(false) }

    // Create a string value to store the selected city
    var mSelectedTextBlockUser by remember { mutableStateOf("") }

    var mTextFieldSizeBlockUser by remember { mutableStateOf(Size.Zero) }

    val iconBlockUser = if (mExpandedBlockUser)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    // Report dialog
    if (openBlockUserDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openBlockUserDialog.value = false
            },
            backgroundColor = Color.Gray,
            text = {
                OutlinedTextField(
                    value = mSelectedTextBlockUser,
                    onValueChange = {
                        mSelectedTextBlockUser = it
                    },
                    modifier = Modifier
                        .width(280.dp)
                        .onGloballyPositioned { coordinates ->
                            // This value is used to assign to
                            mTextFieldSize = coordinates.size.toSize()
                        },
                    label = { Text("Select user") },
                    trailingIcon = {
                        Icon(icon, "contentDescription",
                            Modifier.clickable { mExpandedBlockUser = !mExpandedBlockUser })
                    }
                )
                DropdownMenu(
                    expanded = mExpandedBlockUser,
                    onDismissRequest = { mExpandedBlockUser = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
                ) {
                    viewModel.allUsers.forEach { user ->
                        DropdownMenuItem(onClick = {
                            mSelectedTextBlockUser = user.username
                            mExpandedBlockUser = false
                        }) {
                            Text(text = user.username)
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
                            println("mSelectedText: " + mSelectedTextBlockUser)
                            viewModel.blockUser(mSelectedTextBlockUser)
                            Toast.makeText(context, "User blocked!", Toast.LENGTH_LONG).show()
                            openBlockUserDialog.value = false
                        }
                    ) {
                        Text("Suspend")
                    }
                    Spacer(Modifier.width(10.dp))
                    Button(
                        onClick = { openBlockUserDialog.value = false }
                    ) {
                        Text("Dismiss")
                    }
                }
            }
        )
    }

}