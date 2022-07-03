package com.ognjenlazic.reddit.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ognjenlazic.reddit.model.Community
import com.ognjenlazic.reddit.model.Post
import com.ognjenlazic.reddit.model.User
import com.ognjenlazic.reddit.network.RetrofitInstance
import com.ognjenlazic.reddit.network.model.UserResponse
import kotlinx.coroutines.launch

class UserScreenViewModel : ViewModel(){
    var errorMessage: String by mutableStateOf("")
    var userId: Int by mutableStateOf(0)
    var userPosts:List<Post> by mutableStateOf(listOf())

    var user by mutableStateOf<UserResponse>(UserResponse())
        private set

    fun getUserInfo(){
        viewModelScope.launch {
            try {
                user = RetrofitInstance.user.getUserById(userId)
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getUserPosts(){
        viewModelScope.launch {
            try {
                userPosts = RetrofitInstance.post.getUserPosts(userId = userId)
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}