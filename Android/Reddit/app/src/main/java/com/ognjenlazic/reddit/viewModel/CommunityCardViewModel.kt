package com.ognjenlazic.reddit.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.ognjenlazic.reddit.model.Community
import com.ognjenlazic.reddit.model.Flair
import com.ognjenlazic.reddit.model.User
import com.ognjenlazic.reddit.network.RetrofitInstance
import com.ognjenlazic.reddit.network.model.CommentResponse
import com.ognjenlazic.reddit.network.model.CommunityBody
import com.ognjenlazic.reddit.network.model.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CommunityCardViewModel : ViewModel() {

    var errorMessage: String by mutableStateOf("")
    var communty: Community by mutableStateOf(Community())
    var tokenDerived: String by mutableStateOf("")
    var isUserPartOfCommunity: Boolean by mutableStateOf(false)
    var user: UserResponse by mutableStateOf(UserResponse())
    var flairList: List<Flair> by mutableStateOf(listOf())
    var allUsers: List<UserResponse> by mutableStateOf(listOf())

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

    fun getCommunityFlairs() {
        viewModelScope.launch {
            try {
                flairList =
                    RetrofitInstance.flair.getCommunityFlairs(communityId = communty.communityId)
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun checkIsUserPartOfCommunity() {
        viewModelScope.launch {
            try {
                var commuityUSersIds = RetrofitInstance.community.getCommunityUsers(communty.communityId)
                if(commuityUSersIds.contains(user.userId)){
                    isUserPartOfCommunity = true
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun joinLeftCommunity() {
        viewModelScope.launch {
            try {
                if (isUserPartOfCommunity) {
                    RetrofitInstance.community.leftCommunity(communty.communityId, user.userId)
                    isUserPartOfCommunity = false

                    Log.d(
                        "JoinLeftCommunity",
                        "User: " + user.userId + " left community: " + communty.communityId
                    )

                } else {
                    RetrofitInstance.community.joinCommunity(communty.communityId, user.userId)
                    isUserPartOfCommunity = true

                    Log.d(
                        "JoinLeftCommunity",
                        "User: " + user.userId + " left community: " + communty.communityId
                    )
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("JoinLeftCommunity", e.message.toString())

            }
        }
    }

    fun getAllUsers() {
        viewModelScope.launch {
            try {
                allUsers = RetrofitInstance.user.getAllUsers()
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("ErrorUser", e.message.toString())
            }
        }
    }

    fun suspendCommunity(suspendReason: String) {
        viewModelScope.launch {
            try {
                var communityBody = CommunityBody()
                communityBody.communityId = communty.communityId
                communityBody.creationDate = "2021-12-12T12:12:12.23"
                communityBody.name = communty.name
                communityBody.rules = communty.rules
                communityBody.suspended = true
                communityBody.suspendedReason = suspendReason
                communityBody.description = communty.description
                communityBody.moderatorId = communty.moderatorId

                var response = RetrofitInstance.community.saveCommunity(communityBody)
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun blockUser(username: String) {
        viewModelScope.launch {
            try {
                Log.d("Banning", username)
                var user = RetrofitInstance.user.getUserByUsername(username)
                Log.d("Banning", user.userId.toString())
                Log.d("Banning", communty.communityId.toString())

                var response =
                    RetrofitInstance.community.banUserInCommunity(user.userId, communty.communityId)
                Log.d("BanSuccess", response.toString())
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("BanError", e.message.toString())
            }
        }
    }
}