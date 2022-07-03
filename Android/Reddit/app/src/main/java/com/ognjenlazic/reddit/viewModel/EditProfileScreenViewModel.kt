package com.ognjenlazic.reddit.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.ognjenlazic.reddit.model.User
import com.ognjenlazic.reddit.network.RetrofitInstance
import com.ognjenlazic.reddit.network.model.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class EditProfileScreenViewModel: ViewModel() {

    var user: UserResponse by mutableStateOf<UserResponse>(UserResponse())

    var errorMessage: String by mutableStateOf("")
    var username: String by mutableStateOf("")
    var description: String by mutableStateOf("")
    var email: String by mutableStateOf("")
    var userId: Int by mutableStateOf(0)
    var tokenDerived: String by mutableStateOf("")

    var infoIsLoaded: Boolean by mutableStateOf(false) // Možda koristiti kasnije

    fun getCurrentUserInfo(token: Flow<String>){
        if(!infoIsLoaded){
            viewModelScope.launch {
                try {
                    token.collect {
                        tokenDerived = it
                        var jwt: JWT = JWT(tokenDerived)
                        var usernameFromToken = jwt.getClaim("sub")
                        user = RetrofitInstance.user.getUserByUsername(usernameFromToken.asString()!!)
                        username = user.username
                        email = user.email
                        description = user.description
                        infoIsLoaded = true
                    }
                }
                catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }
    }

    fun saveUser(){
        viewModelScope.launch {
            var userToSave = User()
            userToSave.username = username
            userToSave.email = email
            userToSave.description = description
            userToSave.userId = user.userId
            userToSave.avatar = user.avatar
            userToSave.displayName = user.displayName
            userToSave.password = user.password
            userToSave.registrationDate = "2021-12-12T12:12:12.23" // Hardcoded value
            userToSave.userId = user.userId
            userToSave.userType = user.userType
            try{
                var userResponse = RetrofitInstance.user.editUser(userToSave)
            } catch (e: Exception){
                errorMessage = e.message.toString()
            }
        }
    }
}