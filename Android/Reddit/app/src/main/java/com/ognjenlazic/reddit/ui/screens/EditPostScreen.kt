package com.ognjenlazic.reddit.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.ognjenlazic.reddit.navigation.Screen

import com.ognjenlazic.reddit.ui.theme.RedditOrangeRed
import com.ognjenlazic.reddit.ui.theme.RedditWhite
import com.ognjenlazic.reddit.util.EditEvent
import com.ognjenlazic.reddit.viewModel.CreatePostViewModel
import com.ognjenlazic.reddit.viewModel.EditPostScreenViewModel
import com.ognjenlazic.reddit.viewModel.RegistrationViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.*


@Composable
fun EditPostScreen(navController: NavController, postId: Int) {
    var viewModel = viewModel(modelClass = EditPostScreenViewModel::class.java)
    viewModel.postId = postId
    viewModel.getPost()

    val newUUID = UUID.randomUUID()
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(androidx.compose.ui.graphics.Color.Gray)
            .padding(30.dp)
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(androidx.compose.ui.graphics.Color.Gray),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Edit post", fontSize = 32.sp)
            Spacer(modifier = Modifier.height(16.dp))


            OutlinedTextField(
                value = viewModel.postTitle,
                onValueChange = {
                    Log.d("Event", "Title event composable " +it)
//                    viewModel.saveInfo(it)
                    viewModel.postTitle = it
                                },
                modifier = Modifier
                    .width(280.dp),
                label = { Text("Title") }
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier
                    .height(150.dp)
                    .width(280.dp),
                value = viewModel.postText,
                label = { Text(text = "Text") },
                onValueChange = {
                    Log.d("Event", "Text event composable")
                    viewModel.postText = it
                },

            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                viewModel.savePost()
                navController.navigate(Screen.Home.route)
            }) {
                Text(text = "Save changes")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.deletePost()
                    navController.navigate("home")
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = androidx.compose.ui.graphics.Color.Red)
            ) {
                Text(
                    text = "Delete",
                    color = androidx.compose.ui.graphics.Color.White,
                )
            }
            Spacer(modifier = Modifier.height(40.dp))

        }

    }
}
