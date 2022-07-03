package com.ognjenlazic.reddit.network.service

import com.ognjenlazic.reddit.model.Community
import com.ognjenlazic.reddit.model.Post
import com.ognjenlazic.reddit.model.User
import com.ognjenlazic.reddit.network.model.PostBody
import retrofit2.Response
import retrofit2.http.*

interface PostService {
    @GET("post")
    suspend fun getAllPosts(): List<Post>

    @GET("post/sorted-by-popular")
    suspend fun getAllPostsSortedByPopular(): List<Post>

    @GET("post/sorted-by-unpopular")
    suspend fun getAllPostsSortedByUnpopular(): List<Post>

    @GET("post/sorted-by-date-desc")
    suspend fun getAllPostsSortedByNewest(): List<Post>

    @GET("post/sorted-by-date-asc")
    suspend fun getAllPostsSortedByOldest(): List<Post>

    @GET("post/community/{communityId}")
    suspend fun getCommunityPosts(@Path("communityId") communityId: Int): List<Post>

    @GET("post/user/{userId}")
    suspend fun getUserPosts(@Path("userId") userId: Int): List<Post>

    @GET("post/{postId}")
    suspend fun getPostById(@Path("postId") postId: Int): Post

    @DELETE("post/{postId}")
    suspend fun deletePost(@Path("postId") postId: Int)

    @POST("/api/post")
    suspend fun createNewPost(@Body post: PostBody): Post

    @PUT("/api/post")
    suspend fun editPost(@Body post: PostBody): Post

}