package com.ognjenlazic.reddit.ui.screens


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ognjenlazic.reddit.navigation.Screen
import com.ognjenlazic.reddit.repository.DataStorePreferenceRepository

import com.ognjenlazic.reddit.viewModel.RegistrationViewModel
import com.ognjenlazic.reddit.viewModel.ResetPasswordViewModel

@Composable
fun ResetPasswordScreen(navController: NavHostController) {

    val coroutineScope = rememberCoroutineScope()
    val viewModel = viewModel(modelClass = ResetPasswordViewModel::class.java)

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
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Reset password", fontSize = 32.sp, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.oldPassword,
                placeholder = { Text(text = "") },
                label = { Text(text = "Old password") },
                onValueChange = { viewModel.oldPassword = it },
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.newPassword,
                label = { Text(text = "New Password") },
                onValueChange = { viewModel.newPassword = it },
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.confirmNewPassword,
                label = { Text(text = "Confirm new Password") },
                onValueChange = { viewModel.confirmNewPassword = it },
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (viewModel.oldPassword.equals("") || viewModel.newPassword.equals("") || viewModel.confirmNewPassword.equals("")){
                    Toast.makeText(context, "Some fields are empty?", Toast.LENGTH_LONG).show()
                } else {
                    viewModel.resetPassword()
                }
//                navController.navigate(Screen.Login.route)
            }) {
                Text(text = "Reset")
            }
        }
    }

}