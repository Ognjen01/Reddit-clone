package com.ognjenlazic.reddit.model

import java.util.*

data class Comment(
    val commentId: Int,
    val deleted: Boolean,
    val postId: Int?,
    val repliesToCommentId: Int?,
    val text: String,
    val timestamp: String,
    val userId: Int
)