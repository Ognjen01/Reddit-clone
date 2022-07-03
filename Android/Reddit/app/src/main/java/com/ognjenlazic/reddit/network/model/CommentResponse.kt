package com.ognjenlazic.reddit.network.model

data class CommentResponse(
    val commentId: Int,
    val deleted: Boolean,
    val postId: Int,
    val repliesToCommentId: Any,
    val text: String,
    val timestamp: List<Int>,
    val userId: Int
){
    constructor(): this(
        0, false, 0, 0, "", emptyList(),  0,
    )
}
