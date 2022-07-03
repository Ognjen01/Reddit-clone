package com.ognjenlazic.reddit.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.ognjenlazic.reddit.navigation.Screen
import com.ognjenlazic.reddit.network.RetrofitInstance
import com.ognjenlazic.reddit.network.model.Login
import com.ognjenlazic.reddit.network.response.Token
import com.ognjenlazic.reddit.repository.DataStorePreferenceRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(
//    private val dataStorePreferenceRepository: DataStorePreferenceRepository
): ViewModel()  {
    lateinit var dataStorePreferenceRepository: DataStorePreferenceRepository
    var token: Token = Token("",0)
    var errorMessage: String by mutableStateOf("")
//    var username: String by mutableStateOf("ognjen.lazic")
//    var password: String by mutableStateOf("qweqwe")
    var successfullLogin: Boolean by mutableStateOf(false)
    lateinit var navController: NavHostController

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val login = Login(password, username)
                val tokenResponse = RetrofitInstance.user.login(login)
                token = tokenResponse
                dataStorePreferenceRepository.setUserInformation(token.accessToken)
                navController.navigate(Screen.Home.route)
                successfullLogin = true

            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("Pref", e.message.toString())
            }
        }
    }

}