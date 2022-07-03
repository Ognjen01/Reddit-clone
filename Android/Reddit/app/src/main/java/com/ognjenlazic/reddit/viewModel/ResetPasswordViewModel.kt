package com.ognjenlazic.reddit.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.ognjenlazic.reddit.network.RetrofitInstance
import com.ognjenlazic.reddit.network.model.ResetPasswordBody
import com.ognjenlazic.reddit.network.model.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ResetPasswordViewModel: ViewModel() {

    var oldPassword: String by mutableStateOf("qweqwe")
    var newPassword: String by mutableStateOf("qweqweqwe")
    var confirmNewPassword: String by mutableStateOf("qweqweqwe")
    var errorMessage: String by mutableStateOf("")
    var tokenDerived: String by mutableStateOf("")
    var user: UserResponse by mutableStateOf(UserResponse())

    fun resetPassword(){
        if (newPassword.equals(confirmNewPassword)){
            viewModelScope.launch {
                try {
                    var body = ResetPasswordBody(confirmNewPassword, oldPassword, user.userId) // Hardcoded value
                    RetrofitInstance.user.resetPassword(body)
                    Log.d("Reset", "Success")

                }
                catch (e: Exception) {
                    errorMessage = e.message.toString()
                    Log.e("Reset", e.message.toString())
                }
            }
        }
    }

    fun getCurrentUserInfo(token: Flow<String>){
        viewModelScope.launch {
            try {
                token.collect {
                    tokenDerived = it
                    var jwt: JWT = JWT(tokenDerived)
                    var username = jwt.getClaim("sub")
                    user = RetrofitInstance.user.getUserByUsername(username.asString()!!)
                }
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}