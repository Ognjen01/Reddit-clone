package com.ognjenlazic.reddit.network.service

import com.ognjenlazic.reddit.model.Report
import com.ognjenlazic.reddit.model.User
import com.ognjenlazic.reddit.network.model.CommentResponse
import com.ognjenlazic.reddit.network.model.ReportResponse
import retrofit2.http.*

interface ReportService {

    @POST("report")
    suspend fun createNew(@Body report: Report): Report

    @GET("report")
    suspend fun getAllReports(): List<ReportResponse>

    @PUT("report")
    suspend fun editReport(@Body report: Report): ReportResponse

    @DELETE("report/{reportId}")
    suspend fun deleteReport(@Path("reportId") reportId: Int)
}