package com.ognjenlazic.reddit.ui.screens


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ognjenlazic.reddit.navigation.Screen
import com.ognjenlazic.reddit.repository.DataStorePreferenceRepository

import com.ognjenlazic.reddit.viewModel.EditProfileScreenViewModel
import java.util.*


@Composable
fun EditProfileScreen(navController: NavController) {
    var viewModel = viewModel(modelClass = EditProfileScreenViewModel::class.java)
    val context = LocalContext.current
    val dataStorePreferenceRepository: DataStorePreferenceRepository =
        DataStorePreferenceRepository.getInstance(context)
    val userInfo = dataStorePreferenceRepository.getUserInformation
    viewModel.getCurrentUserInfo(userInfo)

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

            Text("Edit profile", fontSize = 32.sp)
            Spacer(modifier = Modifier.height(16.dp))


            OutlinedTextField(
                value = viewModel.username,
                onValueChange = {
                    viewModel.username = it
                },
                modifier = Modifier
                    .width(280.dp),
                label = { Text("username") }
            )

            OutlinedTextField(
                value = viewModel.email,
                onValueChange = {
                    viewModel.email = it
                },
                modifier = Modifier
                    .width(280.dp),
                label = { Text("e-mail") }
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier
                    .height(150.dp)
                    .width(280.dp),
                value = viewModel.description,
                label = { Text(text = "Description") },
                onValueChange = {
                    viewModel.description = it
                },
                )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                viewModel.saveUser()
                navController.navigate(Screen.Login.route)
                Toast.makeText(context, "Please login again", Toast.LENGTH_LONG).show()

            }) {
                Text(text = "Save changes")
            }
            Spacer(modifier = Modifier.height(40.dp))

        }

    }
}
