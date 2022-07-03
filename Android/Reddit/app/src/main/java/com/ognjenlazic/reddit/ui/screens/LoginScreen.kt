package com.ognjenlazic.reddit.ui.screens

import android.util.Log
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
import androidx.navigation.compose.rememberNavController
import com.ognjenlazic.reddit.navigation.Screen
import com.ognjenlazic.reddit.navigation.SetupBottomNavGraph
import com.ognjenlazic.reddit.navigation.SetupLogoutNavGraph
import com.ognjenlazic.reddit.navigation.SetupNavGraph
import com.ognjenlazic.reddit.repository.DataStorePreferenceRepository
import com.ognjenlazic.reddit.repository.DataStoreViewModelFactory
import com.ognjenlazic.reddit.viewModel.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController,
//                loginViewModel: LoginViewModel = viewModel(
//                    factory = DataStoreViewModelFactory(DataStorePreferenceRepository(LocalContext.current))
//                )
) {
    val context = LocalContext.current

    var loginViewModel = LoginViewModel()
    val dataStorePreferenceRepository: DataStorePreferenceRepository =
        DataStorePreferenceRepository.getInstance(context)
    loginViewModel.dataStorePreferenceRepository = dataStorePreferenceRepository
    val coroutineScope = rememberCoroutineScope()
    loginViewModel.navController = navController

    var username by remember { mutableStateOf("vladimir.djurdjevic") }
    var password by remember { mutableStateOf("qweqwe") }


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
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            Text("Login", fontSize = 32.sp, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = username,
                label = { Text(text = "username") },
                onValueChange = { username = it },
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(text = "password") },
                onValueChange = {password = it},
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                loginViewModel.login(username = username, password = password)
//                navController.navigate(Screen.Home.route)

            }) {
                Text(text = "Login")
            }
            Spacer(Modifier.height(10.dp))
            Button(onClick = {
                navController.navigate(Screen.Register.route)
            }) {
                Text(text = "Register")
            }
        }
    }
}