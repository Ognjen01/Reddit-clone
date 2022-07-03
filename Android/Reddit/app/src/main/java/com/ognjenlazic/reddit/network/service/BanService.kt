package com.ognjenlazic.reddit.network.service

import com.ognjenlazic.reddit.network.model.Login
import com.ognjenlazic.reddit.network.response.Token
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface BanService {
    @POST("/banned")
    suspend fun banUserInCommunity(@Path("userId") userId: Int, @Path("communityId") communityId: Int): Token
}