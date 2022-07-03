package com.ognjenlazic.reddit.network.service

import com.ognjenlazic.reddit.model.Community
import com.ognjenlazic.reddit.model.Flair
import com.ognjenlazic.reddit.model.Rule
import com.ognjenlazic.reddit.model.User
import com.ognjenlazic.reddit.network.model.FlairCommunityRelation
import retrofit2.http.*

interface FlairService {

    @GET("flair")
    suspend fun getAllFLairs(): List<Flair>

    @GET("flair/community/{communityId}")
    suspend fun getCommunityFlairs(@Path("communityId") communityId: Int): List<Flair>

    @GET("flair/{flairId}")
    suspend fun getFlairId(@Path("flairId") flairId: Int): Flair

    @POST("flair")
    suspend fun createFlair(@Body flair: Flair): Flair

    @POST("flair/flair-community")
    suspend fun createFlairCommunityRelation(@Body relation: FlairCommunityRelation): Flair

    @PUT("flair")
    suspend fun updateFlair(@Body flair: Flair): Flair

    @DELETE("flair/{flairId}")
    suspend fun deleteFlair(@Path("flairId") flairId: Int)
}