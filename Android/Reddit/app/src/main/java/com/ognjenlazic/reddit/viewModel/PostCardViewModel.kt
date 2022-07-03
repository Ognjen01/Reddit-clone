package com.ognjenlazic.reddit.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.auth0.android.jwt.JWT
import com.ognjenlazic.reddit.model.*
import com.ognjenlazic.reddit.network.RetrofitInstance
import com.ognjenlazic.reddit.network.model.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class PostCardViewModel(postInj: Post) : ViewModel() {

    var post: Post = Post()

    var user by mutableStateOf<UserResponse>(UserResponse())
        private set

    var community by mutableStateOf<Community>(Community())
        private set

    var karma by mutableStateOf<Karma>(Karma())
        private set

    var errorMessage: String by mutableStateOf("")
    var username: String by mutableStateOf("")
    var reportReason: String by mutableStateOf("")
    var commentText: String by mutableStateOf("")
    var reactedUpvote: Boolean by mutableStateOf(false)
    var reactedDownvote: Boolean by mutableStateOf(false)
    var isAlreadyReacted: Boolean by mutableStateOf(false)
    var areInfoLoaded: Boolean by mutableStateOf(false)
    var userBlockedByThisCommunity: Boolean by mutableStateOf(false)
    var tokenDerived: String by mutableStateOf("")
    var flair: String by mutableStateOf("")

    var currentUser by mutableStateOf<UserResponse>(UserResponse())
        private set

    init {
        post = postInj
        getUser(postInj)
        getCommunity(postInj)
        getKarma(postInj)
        getFlair(postInj)
        checkIsAlreadyReacted(postInj)
    }

    fun getInformation() {
        if(!areInfoLoaded){
            // Try this, but it slowdown everything
        }
    }

    fun getUser(post: Post) {
        viewModelScope.launch {
            try {
                val userResponse = RetrofitInstance.user.getUserById(post.userId)
                user = userResponse
                username = userResponse.username
                Log.d("User", user.toString())
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun checkIsUserBlockedByCommunity(post: Post) {
        viewModelScope.launch {
            try {
                val blockedIds = RetrofitInstance.community.getCommunityBlockedUsers(post.communityId)
                if(blockedIds.contains(currentUser.userId)){
                    userBlockedByThisCommunity = true
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun checkIsAlreadyReacted(post: Post) {
        viewModelScope.launch {
            try {
                val reactions = RetrofitInstance.reaction.getAllPostReactions(post.id)
                for (reaction in reactions) {
                    if (reaction.userId == currentUser.userId) {
                        isAlreadyReacted = true
                        if (reaction.reactionType == "UPVOTE") {
                            reactedUpvote = true
                        } else if (reaction.reactionType == "DOWNVOTE") {
                            reactedDownvote = true
                        }
                    }
                }
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
                    currentUser = RetrofitInstance.user.getUserByUsername(username.asString()!!)
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getCommunity(post: Post) {
        viewModelScope.launch {
            try {
                val communityResponse =
                    RetrofitInstance.community.getCommunityById(post.communityId)
                community = communityResponse
                Log.d("Community", community.toString())
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getFlair(post: Post) {
        viewModelScope.launch {
            try {
                val flairResponse = RetrofitInstance.flair.getFlairId(post.flairId)
                flair = flairResponse.name
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun report() {
        Log.d("REASON  ", reportReason)
//        println(reportReason)
    }

    fun comment(post: Post) {
        if(!userBlockedByThisCommunity){
            viewModelScope.launch {
                try {
                    var comment = Comment(
                        50,
                        false,
                        post.id,
                        null,
                        commentText,
                        "2022-07-03T17:14:14",
                        currentUser.userId
                    ) // Hardcoded value
                    val commentResponse = RetrofitInstance.comment.createNew(comment)
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }
    }

    fun react(reactionType: String, post: Post) {
        if (!reactedDownvote && !reactedUpvote && !isAlreadyReacted && !userBlockedByThisCommunity) {
            viewModelScope.launch {
                try {
                    val sdf =
                        SimpleDateFormat("yyyy-dd-MM hh:mm:ss") // Pokušati staviti T između kao 2022-01-12T17:14:14
                    val currentDate = sdf.format(Date())
                    var reaction = Reaction(
                        null,
                        post.id,
                        10,
                        reactionType,
                        "2022-07-03T17:14:14",
                        currentUser.userId
                    ) // Harcoded information
                    val reactionResponse = RetrofitInstance.reaction.createReaction(reaction)
                    isAlreadyReacted = true
                    if (reactionType.equals("UPVOTE")) {
                        reactedUpvote = true
                    } else {
                        reactedDownvote = true
                    }
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }
    }

    fun getKarma(post: Post) {
        viewModelScope.launch {
            try {
                val karmaResponse = RetrofitInstance.reaction.getPostKarma(post.id)
                karma = karmaResponse
                Log.d("Community", community.toString())
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

}