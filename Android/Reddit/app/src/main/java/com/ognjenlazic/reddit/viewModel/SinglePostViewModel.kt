package com.ognjenlazic.reddit.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.ognjenlazic.reddit.model.Comment
import com.ognjenlazic.reddit.model.Community
import com.ognjenlazic.reddit.model.Post
import com.ognjenlazic.reddit.model.User
import com.ognjenlazic.reddit.network.RetrofitInstance
import com.ognjenlazic.reddit.network.model.CommentResponse
import com.ognjenlazic.reddit.network.model.UserResponse
import com.ognjenlazic.reddit.repository.DataStorePreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SinglePostViewModel(newPostId: Int) : ViewModel() {

    var errorMessage: String by mutableStateOf("")
    var selectedSortType: String by mutableStateOf("")
    var tokenDerived: String by mutableStateOf("")
    var infoLoaded: Boolean by mutableStateOf(false)
    var infoCommentLoaded: Boolean by mutableStateOf(false)
    var postId = newPostId
    var user: UserResponse by mutableStateOf(UserResponse())

    var community by mutableStateOf<Community>(Community())
        private set

    var post by mutableStateOf<Post>(Post())
        private set

    var comments: List<CommentResponse> by mutableStateOf(listOf())


    init {
//        getPost()
//        viewModelScope.launch {
//            try {
//                when(selectedSortType){
//                    "Popular" -> {
//                        comments = RetrofitInstance.comment.getAllCommentsSortedByUnpopular(postId)
//                    }
//                    "Unpopular" -> {
//                        comments = RetrofitInstance.comment.getAllCommentsSortedByPopular(postId)
//
//                    }
//                    "Newest" -> {
//                        comments = RetrofitInstance.comment.getAllCommentsSortedByNewest(postId)
//                    }
//                    "Oldest" -> {
//                        comments = RetrofitInstance.comment.getAllCommentsSortedByOldest(postId)
//                    }
//                    "" -> {
//                        comments = RetrofitInstance.comment.getAllPostComments(postId)
//                    }
//                }
//            }
//            catch (e: Exception) {
//                errorMessage = e.message.toString()
//                Log.e("Error", e.message.toString())
//            }
//        }
    }

    fun getPost() {

        viewModelScope.launch {
            try {
                post = RetrofitInstance.post.getPostById(postId)
                infoLoaded = true
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }

    }

    fun getComments() {

        viewModelScope.launch {
            try {
                comments = RetrofitInstance.comment.getAllPostComments(postId)
                Log.d("CommentL", comments.size.toString())
                infoCommentLoaded = true
            } catch (e: Exception) {
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
                    user = RetrofitInstance.user.getUserByUsername(username.asString()!!)
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getSortedComments() {
        viewModelScope.launch {
            try {
                when (selectedSortType) {
                    "Popular" -> {
                        comments = RetrofitInstance.comment.getAllCommentsSortedByUnpopular(postId)
                    }
                    "Unpopular" -> {
                        comments = RetrofitInstance.comment.getAllCommentsSortedByPopular(postId)

                    }
                    "Newest" -> {
                        comments = RetrofitInstance.comment.getAllCommentsSortedByNewest(postId)
                    }
                    "Oldest" -> {
                        comments = RetrofitInstance.comment.getAllCommentsSortedByOldest(postId)
                    }
                    "" -> {
                        comments = RetrofitInstance.comment.getAllPostComments(postId)
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("Error", e.message.toString())
            }
        }
    }
}