package com.ognjenlazic.reddit.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.ognjenlazic.reddit.model.Community
import com.ognjenlazic.reddit.network.RetrofitInstance
import com.ognjenlazic.reddit.network.model.CommunityBody
import com.ognjenlazic.reddit.network.model.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CreateCommunityViewModel: ViewModel() {

    var errorMessage: String by mutableStateOf("")
    var name: String by mutableStateOf("")
    var description: String by mutableStateOf("")
    var tokenDerived: String by mutableStateOf("")

    var currentUser by mutableStateOf<UserResponse>(UserResponse())
        private set

    fun createCommunity(){
        viewModelScope.launch {
            try {
                val newCommunity = CommunityBody(10, "2022-01-12T17:14:14", description, currentUser.userId, name, null, false, null)
                val communityResponse = RetrofitInstance.community.createNewCommunity(newCommunity)
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getCurrentUserInfo(token: Flow<String>) {
        viewModelScope.launch {
            try {
                token.collect {
                    tokenDerived = it
                    var jwt: JWT = JWT(tokenDerived)
                    var username = jwt.getClaim("sub")
                    currentUser = RetrofitInstance.user.getUserByUsername(username.asString()!!)
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}