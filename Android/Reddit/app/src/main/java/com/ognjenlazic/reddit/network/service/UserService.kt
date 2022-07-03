package com.ognjenlazic.reddit.network.service

import com.ognjenlazic.reddit.model.User
import com.ognjenlazic.reddit.network.model.UserResponse
import com.ognjenlazic.reddit.network.model.Login
import com.ognjenlazic.reddit.network.model.ResetPasswordBody
import com.ognjenlazic.reddit.network.response.Token
import retrofit2.http.*

interface UserService {

    @GET("user")
    suspend fun getAllUsers(): List<UserResponse>

    @GET("user/{id}")
    suspend fun getUserById(@Path("id") id: Int): UserResponse

    @GET("user/username/{username}")
    suspend fun getUserByUsername(@Path("username") username: String): UserResponse

    @POST("/api/user/login")
    suspend fun login(@Body loginData: Login): Token

    @POST("/api/user")
    suspend fun register(@Body user: User): User

    @PUT("user/resetPassword")
    suspend fun resetPassword(@Body resetPasswordBody: ResetPasswordBody)

    @PUT("/api/user")
    suspend fun editUser(@Body user: User): UserResponse
}