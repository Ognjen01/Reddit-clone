package com.ognjenlazic.reddit.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ognjenlazic.reddit.R
import com.ognjenlazic.reddit.navigation.Screen
import com.ognjenlazic.reddit.navigation.SetupBottomNavGraph
import com.ognjenlazic.reddit.navigation.SetupLogoutNavGraph
import com.ognjenlazic.reddit.navigation.SetupNavGraph
import com.ognjenlazic.reddit.repository.DataStorePreferenceRepository
import com.ognjenlazic.reddit.ui.theme.RedditOrange
import com.ognjenlazic.reddit.ui.theme.RedditRed
import com.ognjenlazic.reddit.ui.theme.RedditWhite
import com.ognjenlazic.reddit.viewModel.ProfileScreenViewModel
import com.ognjenlazic.reddit.viewModel.SinglePostViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(navHostController: NavHostController) {

    val avatar: Painter = painterResource(id = R.drawable.avatar_one)
    val viewModel = viewModel(modelClass = ProfileScreenViewModel::class.java)
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val dataStorePreferenceRepository: DataStorePreferenceRepository =
        DataStorePreferenceRepository.getInstance(context)
    val userInfo = dataStorePreferenceRepository.getUserInformation

    Log.d("SharedPref", "Profile screen userinfo -> " + userInfo.toString())
    viewModel.getCurrentUserInfo(userInfo)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
            .padding(30.dp),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .aspectRatio(avatar.intrinsicSize.width / avatar.intrinsicSize.height),
                painter = avatar,
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = viewModel.user.username,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = viewModel.user.description,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                          navHostController.navigate("edit_curren_user_profile")
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = RedditOrange)
            ) {
                Text(
                    text = "Change information",
                    color = Color.Black,
                    )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    navHostController.navigate("reset_password")
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = RedditOrange)
            ) {
                Text(
                    text = "Reset password",
                    color = Color.Black,
                    )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    coroutineScope.launch {
                        dataStorePreferenceRepository.removeUser()
                        navHostController.popBackStack()
                        navHostController.navigate("transition")

                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text(
                    text = "Logout",
                    color = Color.White,
                )
            }
        }
    }
}

