package com.ognjenlazic.reddit.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ognjenlazic.reddit.navigation.Screen
import com.ognjenlazic.reddit.repository.DataStorePreferenceRepository
import com.ognjenlazic.reddit.ui.theme.RedditWhite
import com.ognjenlazic.reddit.viewModel.CreateCommunityViewModel
import com.ognjenlazic.reddit.viewModel.RegistrationViewModel

@Composable
fun CreateCommunity(navController: NavHostController) {

    val coroutineScope = rememberCoroutineScope()
    val viewModel = viewModel(modelClass = CreateCommunityViewModel::class.java)
    val context = LocalContext.current
    val dataStorePreferenceRepository: DataStorePreferenceRepository =
        DataStorePreferenceRepository.getInstance(context)
    val userInfo = dataStorePreferenceRepository.getUserInformation
    viewModel.getCurrentUserInfo(userInfo)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(androidx.compose.ui.graphics.Color.Gray)
            .padding(30.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.Gray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Create community", fontSize = 32.sp)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.name,
                label = { Text(text = "Community name") },
                onValueChange = {viewModel.name = it},
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier
                    .height(300.dp)
                    .width(280.dp),
                value = viewModel.description,
                label = { Text(text = "Description") },
                onValueChange = {viewModel.description = it},
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if(viewModel.name.equals("") || viewModel.description.equals("")){
                    Toast.makeText(context, "Input community name and description!", Toast.LENGTH_LONG).show()
                } else {
                    println("Creating community")
                    viewModel.createCommunity()
                    navController.navigate(Screen.Home.route)
                    Toast.makeText(context, "Create post in new community!", Toast.LENGTH_LONG).show()
                }

            }) {
                Text(text = "Create")
            }
        }
    }
}