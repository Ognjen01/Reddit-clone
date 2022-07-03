package com.ognjenlazic.reddit.model

import java.util.*

data class Reaction(
    val commentId: Int?,
    val postId: Int?,
    val reactionId: Int,
    val reactionType: String,
    val timestamp: String,
    val userId: Int
)