package com.ognjenlazic.reddit.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ognjenlazic.reddit.model.User
import com.ognjenlazic.reddit.network.RetrofitInstance
import com.ognjenlazic.reddit.network.model.Login
import com.ognjenlazic.reddit.network.response.Token
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*

class RegistrationViewModel: ViewModel() {

    var errorMessage: String by mutableStateOf("")
    var username: String by mutableStateOf("zivkovic.slobodan")
    var displayName: String by mutableStateOf("Sloba")
    var description: String by mutableStateOf("Polu covek polu poljoprivrednik")
    var password: String by mutableStateOf("qweqwe")
    var email: String by mutableStateOf("sloba@gmail.com")
    var confirmPassword: String by mutableStateOf("qweqwe")

    fun register() {
        if(password.equals(confirmPassword)){
            viewModelScope.launch {
                try {
                    val newUser = User("https://i.redd.it/7z6purnyuvx51.png", description, displayName, email, password,
                        "2021-12-12T12:12:12.23", 20, "USER", username) // Add date time value couse it support API level 26
                    val userResponse = RetrofitInstance.user.register(newUser)
                }
                catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        } else {
            println("Confirm your password")
        }
    }
}