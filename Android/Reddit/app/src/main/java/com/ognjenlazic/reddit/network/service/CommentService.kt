package com.ognjenlazic.reddit.network.service

import com.ognjenlazic.reddit.model.Comment
import com.ognjenlazic.reddit.model.Post
import com.ognjenlazic.reddit.network.model.CommentResponse
import retrofit2.http.*

interface CommentService {

    @GET("comment")
    suspend fun getAllComments(): List<Comment>

    @GET("comment/post/{postid}")
    suspend fun getAllPostComments(@Path("postid") postid: Int): List<CommentResponse>

    @GET("comment/comment/{commentId}")
    suspend fun getAllCommentComments(@Path("commentId") commentId: Int): List<CommentResponse>

    @GET("comment/{commentId}")
    suspend fun getCommentById(@Path("commentId") commentId: Int): CommentResponse

    @DELETE("comment/{commentId}")
    suspend fun deleteComment(@Path("commentId") commentId: Int)

    @POST("comment")
    suspend fun createNew(@Body comment: Comment): Comment

    @GET("comment/post/{postId}/sorted-by-popular")
    suspend fun getAllCommentsSortedByPopular(@Path("postId") postId: Int): List<CommentResponse>

    @GET("comment/post/{postId}/sorted-by-unpopular")
    suspend fun getAllCommentsSortedByUnpopular(@Path("postId") postId: Int): List<CommentResponse>

    @GET("comment/post/{postId}/sorted-by-date-desc")
    suspend fun getAllCommentsSortedByNewest(@Path("postId") postId: Int): List<CommentResponse>

    @GET("comment/post/{postId}/sorted-by-date-asc")
    suspend fun getAllCommentsSortedByOldest(@Path("postId") postId: Int): List<CommentResponse>
}