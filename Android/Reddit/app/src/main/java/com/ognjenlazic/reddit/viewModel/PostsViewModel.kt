package com.ognjenlazic.reddit.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ognjenlazic.reddit.model.Post
import com.ognjenlazic.reddit.network.RetrofitInstance
import kotlinx.coroutines.launch


class PostsViewModel () : ViewModel() {
    var posts:List<Post> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")
    var selectedSortType: String by mutableStateOf("")
    var infoLoaded: Boolean by mutableStateOf(false)
    init {
        viewModelScope.launch {
            try {
                when(selectedSortType){
                    "Popular" -> {
                        posts = RetrofitInstance.post.getAllPostsSortedByUnpopular()
                    }
                    "Unpopular" -> {
                        posts = RetrofitInstance.post.getAllPostsSortedByPopular()

                    }
                    "Newest" -> {
                        posts = RetrofitInstance.post.getAllPostsSortedByNewest()
                    }
                    "Oldest" -> {
                        posts = RetrofitInstance.post.getAllPostsSortedByOldest()
                    }
                    "" -> {
                        posts = RetrofitInstance.post.getAllPosts()
                    }
                }
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("Error", e.message.toString())
            }
        }
    }

    fun getPosts() {

        viewModelScope.launch {
            try {
                val postList = RetrofitInstance.post.getAllPosts()
                posts = postList
                Log.d("API", "Api successfuly called")
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }

        posts.forEach {
            Log.d("Post", it.toString())
        }
    }

    fun getSortedPosts(sortType: String){
        viewModelScope.launch {
            try {
                when(sortType){
                    "Popular" -> {
                        posts = RetrofitInstance.post.getAllPostsSortedByPopular()
                    }
                    "Unpopular" -> {
                        posts = RetrofitInstance.post.getAllPostsSortedByUnpopular()
                    }
                    "Newest" -> {
                        posts = RetrofitInstance.post.getAllPostsSortedByNewest()
                    }
                    "Oldest" -> {
                        posts = RetrofitInstance.post.getAllPostsSortedByOldest()
                    }
                }
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("Error", e.message.toString())
            }
        }
    }
}