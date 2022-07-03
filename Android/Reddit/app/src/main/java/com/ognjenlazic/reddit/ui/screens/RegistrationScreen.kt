package com.ognjenlazic.reddit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsProperties.Password
import androidx.compose.ui.text.input.KeyboardType.Companion.Email
import androidx.compose.ui.text.input.KeyboardType.Companion.Password
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ognjenlazic.reddit.navigation.Screen
import com.ognjenlazic.reddit.ui.theme.RedditOrange
import com.ognjenlazic.reddit.ui.theme.RedditWhite
import com.ognjenlazic.reddit.viewModel.LoginViewModel
import com.ognjenlazic.reddit.viewModel.RegistrationViewModel

@Composable
fun RegistrationScreen(navController: NavHostController) {

    val coroutineScope = rememberCoroutineScope()
    val viewModel = viewModel(modelClass = RegistrationViewModel::class.java)

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

            Text("Registration", fontSize = 32.sp, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.username,
                placeholder = { Text(text = "") },
                label = { Text(text = "username") },
                onValueChange = { viewModel.username = it },
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.email,
                label = { Text(text = "email") },
                onValueChange = { viewModel.email = it },
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.displayName,
                label = { Text(text = "display name") },
                onValueChange = { viewModel.displayName = it },
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.description,
                label = { Text(text = "description") },
                onValueChange = { viewModel.description = it },
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.password,
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(text = "password") },
                onValueChange = { viewModel.password = it },
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.confirmPassword,
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(text = "confirm password") },
                onValueChange = { viewModel.confirmPassword = it },
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                viewModel.register()
                navController.navigate(Screen.Login.route)
            }) {
                Text(text = "Register")
            }
        }
    }

}