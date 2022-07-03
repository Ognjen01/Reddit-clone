package com.ognjenlazic.reddit.network.model

data class ReactionResponse(
    val commentId: Any,
    val postId: Int,
    val reactionId: Int,
    val reactionType: String,
    val timestamp: List<Int>,
    val userId: Int
)