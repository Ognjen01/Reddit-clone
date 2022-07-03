package com.ognjenlazic.reddit.model

import com.ognjenlazic.reddit.network.model.UserResponse

data class Post(
    val communityId: Int,
    val creationDate: List<Int>,
    val flairId: Int,
    val id: Int,
    val imagePath: String?,
    var text: String,
    var title: String,
    val userId: Int,
    var user: UserResponse
){
    constructor(): this(
        0, emptyList(), 0, 0, "", "", "", 0, UserResponse()
    )
}