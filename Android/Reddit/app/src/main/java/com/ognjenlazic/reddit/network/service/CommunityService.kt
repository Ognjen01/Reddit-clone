package com.ognjenlazic.reddit.network.service

import com.ognjenlazic.reddit.model.Community
import com.ognjenlazic.reddit.model.User
import com.ognjenlazic.reddit.network.model.CommunityBody
import com.ognjenlazic.reddit.network.response.Token
import retrofit2.http.*

interface CommunityService {

    @GET("community")
    suspend fun getAllComunities(): List<Community>

    @GET("community/{id}")
    suspend fun getCommunityById(@Path("id") id: Int): Community

    @GET("community/community-blocked-users/{id}")
    suspend fun getCommunityBlockedUsers(@Path("id") id: Int): List<Int>

    @GET("community/community-users/{communityId}")
    suspend fun getCommunityUsers(@Path("communityId") communityId: Int): List<Int>

    @POST("community")
    suspend fun createNewCommunity(@Body community: CommunityBody): Community

    @POST("community/community/{communityId}/user/{userId}")
    suspend fun joinCommunity(@Path("communityId") communityId: Int, @Path("userId") userId: Int )

    @DELETE("community/community/{communityId}/user/{userId}")
    suspend fun leftCommunity(@Path("communityId") communityId: Int, @Path("userId") userId: Int )

    @POST("community/block/community/{communityId}/user/{userId}")
    suspend fun banUserInCommunity(@Path("userId") userId: Int, @Path("communityId") communityId: Int): Token

    @PUT("community")
    suspend fun saveCommunity(@Body community: CommunityBody): Community
}