package com.ognjenlazic.reddit.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
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
import com.ognjenlazic.reddit.repository.DataStorePreferenceRepository

import com.ognjenlazic.reddit.ui.theme.RedditOrangeRed
import com.ognjenlazic.reddit.ui.theme.RedditWhite
import com.ognjenlazic.reddit.viewModel.CreatePostViewModel
import com.ognjenlazic.reddit.viewModel.RegistrationViewModel
import kotlinx.coroutines.CompletableDeferred
import java.io.ByteArrayOutputStream
import java.util.*


@Composable
fun CreatePostScreen(navController: NavController) {
    val viewModel = viewModel(modelClass = CreatePostViewModel::class.java)

    val newUUID = UUID.randomUUID()
    var storageRef = FirebaseStorage.getInstance().getReference(newUUID.toString())

    var imageUrl by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUrl = uri
    }

    var selectedFlairId by remember { mutableStateOf(0) }
    var selectedCommunityId by remember { mutableStateOf(0) }


    var mExpandedCommunity by remember { mutableStateOf(false) }

    val mCommunities = viewModel.communityList
    var mSelectedTextCommunity by remember { mutableStateOf("") }
    var mTextFieldSizeCommunity by remember { mutableStateOf(Size.Zero) }

    val icon = if (mExpandedCommunity)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    var mExpandedFlair by remember { mutableStateOf(false) }

    val mFlairs = viewModel.flairList
    var mSelectedTextFlair by remember { mutableStateOf("") }
    var mTextFieldSizeFlair by remember { mutableStateOf(Size.Zero) }

    val iconFlair = if (mExpandedFlair)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    val coroutineScope = rememberCoroutineScope()

    val dataStorePreferenceRepository: DataStorePreferenceRepository =
        DataStorePreferenceRepository.getInstance(context)
    val userInfo = dataStorePreferenceRepository.getUserInformation
    viewModel.getCurrentUserInfo(userInfo)

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

            Text("Create post", fontSize = 32.sp)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = mSelectedTextCommunity,
                onValueChange = { mSelectedTextCommunity = it },
                modifier = Modifier
                    .width(280.dp)
                    .onGloballyPositioned { coordinates ->
                        // This value is used to assign to
                        // the DropDown the same width
                        mTextFieldSizeCommunity = coordinates.size.toSize()
                    },
                label = { Text("Community") },
                trailingIcon = {
                    Icon(icon, "contentDescription",
                        Modifier.clickable { mExpandedCommunity = !mExpandedCommunity })
                }
            )
            DropdownMenu(
                expanded = mExpandedCommunity,
                onDismissRequest = { mExpandedCommunity = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { mTextFieldSizeCommunity.width.toDp() })
            ) {
                mCommunities.forEach { community ->
                    DropdownMenuItem(onClick = {
                        mSelectedTextCommunity = community.name
                        selectedCommunityId = community.communityId
                        viewModel.findCommunityFlairs(communityId = community.communityId)
                        mExpandedCommunity = false
                    }) {
                        Text(text = community.name)
                    }
                }
            }

            OutlinedTextField(
                value = viewModel.title,
                onValueChange = { viewModel.title = it},
                modifier = Modifier
                    .width(280.dp),
                label = { Text("Title") }
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier
                    .height(150.dp)
                    .width(280.dp),
                value = viewModel.text,
                label = { Text(text = "Text") },
                onValueChange = {viewModel.text = it},
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = mSelectedTextFlair,
                onValueChange = {
                    mSelectedTextFlair = it
                                },
                modifier = Modifier
                    .width(280.dp)
                    .onGloballyPositioned { coordinates ->
                        mTextFieldSizeFlair = coordinates.size.toSize()
                    },
                label = { Text("Flair") },
                trailingIcon = {
                    Icon(iconFlair, "contentDescription",
                        Modifier.clickable { mExpandedFlair = !mExpandedFlair })
                }
            )
            DropdownMenu(
                expanded = mExpandedFlair,
                onDismissRequest = { mExpandedFlair = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { mTextFieldSizeFlair.width.toDp() })
            ) {
                mFlairs.forEach { flair ->
                    DropdownMenuItem(onClick = {
                        mSelectedTextFlair = flair.name
                        selectedFlairId = flair.flairId
                        mExpandedFlair = false
                    }) {
                        Text(text = flair.name)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            imageUrl?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                }

                bitmap.value?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Gallery Image",
                        modifier = Modifier.size(400.dp)
                    )
                }
            }
            Button(onClick = {
                launcher.launch("image/*")
            }) {
                Text(text = "Add photo")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                println("Referenca ka slici: " + bitmap.value)
                Log.d("SLIKICA", bitmap.value.toString())

                if(viewModel.text.equals("") || viewModel.title.equals("")){
                    Toast.makeText(context, "Please input title and text!", Toast.LENGTH_SHORT).show()
                } else {
                    if(bitmap.value != null){
                        val baos = ByteArrayOutputStream()
                        bitmap.value!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                        val data = baos.toByteArray()

                        var uploadTask = storageRef.putBytes(data)
                        uploadTask.addOnFailureListener {

                        }.addOnSuccessListener { taskSnapshot ->
                            // Get url of uploaded item
                            storageRef.downloadUrl.addOnSuccessListener {
                                val url = it
                                println("Download Url: " + url)
                                viewModel.imagePath = url.toString()
                                viewModel.createNewPost(
                                    communityId = selectedCommunityId,
                                    flairId = selectedFlairId
                                )
                            }
                        }
                    } else {
                        viewModel.imagePath = null
                        viewModel.createNewPost(
                            communityId = selectedCommunityId,
                            flairId = selectedFlairId
                        )
                        navController.navigate("home")
                        Toast.makeText(context, "Post created!", Toast.LENGTH_LONG).show()
                    }
                }
            }) {
                Text(text = "Create post")
            }
            Spacer(modifier = Modifier.height(40.dp))
        }

    }
}
