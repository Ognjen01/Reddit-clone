package com.ognjenlazic.reddit.network.model

data class ReportResponse(
    var accepted: Boolean,
    val commentId: Int?,
    val postId: Int?,
    val reason: String,
    val reportId: Int,
    val timestamp: List<Int>,
    val userId: Int
){
    constructor(): this(
        false, 0,0,"",0, emptyList(),0
    )
}