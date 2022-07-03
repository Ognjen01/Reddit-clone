package com.ognjenlazic.reddit.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.ognjenlazic.reddit.network.RetrofitInstance
import com.ognjenlazic.reddit.network.model.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SplashScreenViewModel: ViewModel() {

    var user: UserResponse by mutableStateOf(UserResponse())
    var errorMessage: String by mutableStateOf("")
    var tokenDerived: String by mutableStateOf("")

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