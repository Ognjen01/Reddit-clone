package com.ognjenlazic.reddit.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.auth0.android.jwt.JWT
import com.ognjenlazic.reddit.navigation.SetupNavGraph
import com.ognjenlazic.reddit.network.RetrofitInstance
import com.ognjenlazic.reddit.network.model.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProfileScreenViewModel: ViewModel() {

    var errorMessage: String by mutableStateOf("")
    var tokenDerived: String by mutableStateOf("")
    var user: UserResponse by mutableStateOf(UserResponse())

    fun getCurrentUserInfo(token: Flow<String>){
        viewModelScope.launch {
            try {
                token.collect {
                    tokenDerived = it
                    Log.d("SharedPref", "Token -> " + it)
                    var jwt: JWT = JWT(tokenDerived)
                    var username = jwt.getClaim("sub")
                    Log.d("SharedPref", "Profile screen ViewModel username from userInfo -> " + username.asString()!!)

                    user = RetrofitInstance.user.getUserByUsername(username.asString()!!)
                }
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("SharedPref", e.message.toString())
            }
        }
    }

    fun getCurrentUserInfoFromInstance(token: String){
        viewModelScope.launch {
            try {

                    var jwt: JWT = JWT(token)
                    var username = jwt.getClaim("sub")
                    Log.d("SharedPref", "Profile screen ViewModel username from userInfo -> " + username.asString()!!)

                    user = RetrofitInstance.user.getUserByUsername(username.asString()!!)

            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("SharedPref", e.message.toString())
            }
        }
    }
}