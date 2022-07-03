package com.ognjenlazic.reddit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ognjenlazic.reddit.ui.theme.RedditWhite

@Composable
fun Settings() {
    val coroutineScope = rememberCoroutineScope()

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

            Text("Edit your profile", fontSize = 32.sp)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "",
                placeholder = { Text(text = "Username01") },
                label = { Text(text = "username") },
                onValueChange = {},
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "",
                placeholder = { Text(text = "user@email.com") },
                label = { Text(text = "email") },
                onValueChange = {},
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "",
                placeholder = { Text(text = "password") },
                label = { Text(text = "password") },
                onValueChange = {},
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "",
                placeholder = { Text(text = "password") },
                label = { Text(text = "confirm password") },
                onValueChange = {},
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { println("Počinjanje Logina") }) {
                Text(text = "Login")
            }
        }
    }

}
/*
@Composable
@Preview
fun SettingsScreenPreview() {
    SettingsScreen()
}*/
