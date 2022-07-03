package com.ognjenlazic.reddit.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ognjenlazic.reddit.model.Post
import com.ognjenlazic.reddit.network.RetrofitInstance
import com.ognjenlazic.reddit.network.model.PostBody
import com.ognjenlazic.reddit.util.EditEvent
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

class EditPostScreenViewModel: ViewModel() {

    var post: Post by mutableStateOf<Post>(Post())

    var errorMessage: String by mutableStateOf("")
    var postTitle: String by mutableStateOf("")
    var postText: String by mutableStateOf("")
    var postId: Int by mutableStateOf(0)

    // this value protect from new call of getPost function
    /*
    TODO: Ovo se može mijenjati tako da se get post funkcija zove u init pozivu, jer se viewModel ne inicjalizuje dva puta
          Ali ovo je nigdje veze
          Jer ako se viewModel ne inicijalizuje dva puta
          Zašto se onda funkcija poziva dva puta...
          Jer se poziva odma nakon inicijalizacie viewModela
          Probati malo kasnije...
     */
    var infoIsLoaded: Boolean by mutableStateOf(false)

    init {
        Log.d("Event", "Ponovo se inicijalizuje viewModel")
    }


    fun getPost(){
        if(!infoIsLoaded){
            viewModelScope.launch {
                try {
                    post = RetrofitInstance.post.getPostById(postId)
                    postTitle = post.title
                    postText = post.text
                    Log.d("Event", "PONOVO JE POZVNA FUNKCIJA")
                    infoIsLoaded = true
                }
                catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }
    }

    fun savePost(){
        viewModelScope.launch {
            var postBody = PostBody()
            postBody.communityId = post.communityId
//            postBody.creationDate = post.creationDate
            postBody.flairId = post.flairId
            postBody.id = post.id
            postBody.imagePath = post.imagePath
            postBody.text = postText
            postBody.title = postTitle
            postBody.userId = post.userId
            try {
                post = RetrofitInstance.post.editPost(postBody)
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun deletePost(){
        viewModelScope.launch {
            try {
                RetrofitInstance.post.deletePost(post.id)
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    // Test function
    fun saveInfo(title: String){
        Log.d("Event", "Title event viewModel " +title)
        postTitle = title
        Log.d("Event", "postTitle is now: " +postTitle)
        postText = "Promjena"
    }
}