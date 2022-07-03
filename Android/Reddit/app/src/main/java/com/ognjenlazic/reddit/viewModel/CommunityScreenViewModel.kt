package com.ognjenlazic.reddit.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ognjenlazic.reddit.model.Community
import com.ognjenlazic.reddit.model.Post
import com.ognjenlazic.reddit.network.RetrofitInstance
import kotlinx.coroutines.launch

class CommunityScreenViewModel : ViewModel(){

    var errorMessage: String by mutableStateOf("")
    var communityId: Int by mutableStateOf(0)
    var communityPosts:List<Post> by mutableStateOf(listOf())

    var community by mutableStateOf<Community>(Community())
        private set

    fun getCommunityInfo(communityId: Int){
        viewModelScope.launch {
            try {
                community = RetrofitInstance.community.getCommunityById(communityId)
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getAllCommunityPosts(communityId: Int){
        viewModelScope.launch {
            try {
                communityPosts = RetrofitInstance.post.getCommunityPosts(communityId = communityId)
                Log.d("RULES", community.rules!!.toString())
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

}