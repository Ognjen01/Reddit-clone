package com.ognjenlazic.reddit.repository

import com.ognjenlazic.reddit.model.User
import com.ognjenlazic.reddit.network.RetrofitInstance
import com.ognjenlazic.reddit.network.model.Login

class UserRepository {
    suspend fun getAllUsers() =
        RetrofitInstance.user.getAllUsers()

    suspend fun login(loginData: Login) =
        RetrofitInstance.user.login(loginData)

    suspend fun register(user: User) =
        RetrofitInstance.user.register(user)
}