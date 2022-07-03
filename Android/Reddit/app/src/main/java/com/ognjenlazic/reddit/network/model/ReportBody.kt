package com.ognjenlazic.reddit.network.model

data class ReportBody(
    val accepted: Boolean,
    val commentId: Any,
    val postId: Any,
    val reason: String,
    val reportId: Int,
    val timestamp: String,
    val userId: Int
)