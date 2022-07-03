package com.ognjenlazic.reddit.network.service

import com.ognjenlazic.reddit.model.Karma
import com.ognjenlazic.reddit.model.Reaction
import com.ognjenlazic.reddit.model.User
import com.ognjenlazic.reddit.network.model.ReactionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReactionService {
    @GET("reaction/post-karma/{id}")
    suspend fun getPostKarma(@Path("id") id: Int): Karma

    @GET("reaction/user-karma/{userId}")
    suspend fun getUserKarma(@Path("userId") id: Long): Karma

    @GET("reaction/comment-karma/{commentId}")
    suspend fun getCommentKarma(@Path("commentId") id: Int): Karma

    @GET("reaction/post/{postId}")
    suspend fun getAllPostReactions(@Path("postId") postId: Int): List<ReactionResponse>

    @GET("reaction/comment/{commentId}")
    suspend fun getAllCommentReactions(@Path("commentId") commentId: Int): List<ReactionResponse>

    @POST("reaction")
    suspend fun createReaction(@Body reaction: Reaction)
}