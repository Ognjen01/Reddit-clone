package com.ognjenlazic.reddit.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.ognjenlazic.reddit.model.*
import com.ognjenlazic.reddit.network.RetrofitInstance
import com.ognjenlazic.reddit.network.model.CommentResponse
import com.ognjenlazic.reddit.network.model.UserResponse
import com.ognjenlazic.reddit.ui.elements.CommentCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CommentCardViewModel : ViewModel() {

    var errorMessage: String by mutableStateOf("")
    var user: UserResponse by mutableStateOf(UserResponse())
    var comment: CommentResponse by mutableStateOf(CommentResponse())
    var reactedDownvote: Boolean by mutableStateOf(false)
    var reactedUpvote: Boolean by mutableStateOf(false)
    var tokenDerived: String by mutableStateOf("")
    var commentText: String by mutableStateOf("")
    var reportReason: String by mutableStateOf("")
    var comments:List<CommentResponse> by mutableStateOf(listOf())
    var userBlockedByThisCommunity: Boolean by mutableStateOf(false)
    var currentUser by mutableStateOf<UserResponse>(UserResponse())
        private set
    var karma by mutableStateOf<Karma>(Karma())
        private set

    fun getCommentUser() {
        viewModelScope.launch {
            try {
                user = RetrofitInstance.user.getUserById(comment.userId)
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getCommentKarma() {
        viewModelScope.launch {
            try {
                val karmaResponse = RetrofitInstance.reaction.getCommentKarma(comment.commentId)
                karma = karmaResponse
                Log.d("CommentKarma", karma.toString())

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("CommentKarma", e.message.toString())
            }
        }
    }

    fun checkIsUserBlockedByCommunity(comment: CommentResponse) {
        viewModelScope.launch {
            try {
                Log.d("Blocked", "Checking is user blocked")
                if (comment.postId != 0){
                    val post = RetrofitInstance.post.getPostById(comment.postId)
                    val blockedIds = RetrofitInstance.community.getCommunityBlockedUsers(post.communityId)
                    Log.d("Blocked", blockedIds.toString())
                    Log.d("Blocked", currentUser.userId.toString())
                    Log.d("Blocked", blockedIds.contains(currentUser.userId).toString())
                    if(blockedIds.contains(user.userId)){
                        userBlockedByThisCommunity = true
                        Log.d("Blocked", "User is bocked")
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("Blocked", e.message.toString())
            }
        }
    }

    fun react(reactionType: String) {
        if (!reactedDownvote && !reactedUpvote && !userBlockedByThisCommunity) {
            viewModelScope.launch {
                try {
                    var reaction = Reaction(
                        comment.commentId,
                        null,
                        10,
                        reactionType,
                        "2022-01-12T17:14:14",
                        user.userId
                    ) // Harcoded information
                    val reactionResponse = RetrofitInstance.reaction.createReaction(reaction)
                    if (reactionType.equals("UPVOTE")) {
                        reactedUpvote = true
                        karma.number += 1
                    } else {
                        reactedDownvote = true
                        karma.number -= 1
                    }
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }
    }

    fun replyToComment() {
        if(!userBlockedByThisCommunity){
            viewModelScope.launch {
                try {
                    var comment = Comment(
                        50,
                        false,
                        null,
                        comment.commentId,
                        commentText,
                        "2022-01-12T17:14:14",
                        user.userId
                    ) // Hardcoded value
                    val commentResponse = RetrofitInstance.comment.createNew(comment)
                    Log.d("NewComment", commentResponse.toString())
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }
    }

    fun reportComment(reason: String) {
        if(!userBlockedByThisCommunity){
            Log.d("Reason", reason)
            viewModelScope.launch {
                try {
                    var report = Report(
                        false,
                        comment.commentId,
                        null,
                        reason,
                        0,
                        "2022-01-12T17:14:14",
                        user.userId
                    ) // Harcoded value

                    try {
                        val newreport = RetrofitInstance.report.createNew(report)
                        Log.d("NewReport", newreport.toString())
                    } catch (e: Exception) {
                        Log.e("Error", e.message.toString())
                    }

                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
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
                    Log.d("Blocked", user.userId.toString())
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("UserGetting", e.message.toString())
            }
        }
    }

    fun getCommentComments(){
        viewModelScope.launch {
            try {
                comments = RetrofitInstance.comment.getAllCommentComments(comment.commentId)
                Log.d("CommentComments", comments.toString())
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}