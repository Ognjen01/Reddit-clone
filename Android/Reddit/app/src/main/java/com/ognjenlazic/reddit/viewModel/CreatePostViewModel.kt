package com.ognjenlazic.reddit.viewModel

import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.ognjenlazic.reddit.model.Community
import com.ognjenlazic.reddit.model.Flair
import com.ognjenlazic.reddit.model.Post
import com.ognjenlazic.reddit.model.User
import com.ognjenlazic.reddit.network.RetrofitInstance
import com.ognjenlazic.reddit.network.model.PostBody
import com.ognjenlazic.reddit.network.model.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class CreatePostViewModel : ViewModel(){

    var errorMessage: String by mutableStateOf("")
    var creationDate: String by mutableStateOf("2021-11-12T14:14:14") // Hardcoded vlaue
    var id: Int by mutableStateOf(0)
    var imagePath: String? by mutableStateOf("")
    var text: String by mutableStateOf("")
    var title: String by mutableStateOf("")
    var userId: Int by mutableStateOf(1)// Hardcoded value -> Get user from shared pref
    var tokenDerived: String by mutableStateOf("")

    var communityList:List<Community> by mutableStateOf(listOf())
    var flairList:List<Flair> by mutableStateOf(listOf())

    var currentUser by mutableStateOf<UserResponse>(UserResponse())
        private set

    init {
//        findCommunityFlairs(1)
        viewModelScope.launch {
            try {
                communityList = RetrofitInstance.community.getAllComunities()
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun justPrint(){
        println("Test")
    }

    fun createNewPost(communityId: Int, flairId: Int) {
            viewModelScope.launch {
                try {
                    Log.d("OBAJAVA", "Kreiranje objave ViewModel")

                    val postBody = PostBody(communityId, creationDate, flairId, id, imagePath, text, title, currentUser.userId)
                    val postResponse = RetrofitInstance.post.createNewPost(postBody)
                    Log.d("OBAJAVA", "Završeno Kreiranje objave ViewModel")

                }
                catch (e: Exception) {
                    errorMessage = e.message.toString()
                    Log.e("error", e.message.toString())
                }
            }
    }

    fun findCommunityFlairs(communityId: Int) {
        viewModelScope.launch {
            try {
                flairList = RetrofitInstance.flair.getCommunityFlairs(communityId)
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