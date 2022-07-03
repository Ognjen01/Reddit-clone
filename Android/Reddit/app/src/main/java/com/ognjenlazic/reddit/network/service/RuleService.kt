package com.ognjenlazic.reddit.network.service

import com.ognjenlazic.reddit.model.Rule
import com.ognjenlazic.reddit.model.User
import com.ognjenlazic.reddit.network.model.SingleRule
import retrofit2.http.*

interface RuleService {
    @GET("rule")
    suspend fun getAllRules(): List<SingleRule>

    @POST("rule")
    suspend fun createRule(@Body rule: SingleRule): SingleRule

    @PUT("rule")
    suspend fun updateRule(@Body rule: SingleRule): SingleRule

    @DELETE("rule/{ruleId}")
    suspend fun deleteRule(@Path("ruleId") ruleId: Int)
}