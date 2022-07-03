package com.ognjenlazic.reddit.network.model

data class PostBody(
    var communityId: Int,
    var creationDate: String,
    var flairId: Int,
    var id: Int,
    var imagePath: String?,
    var text: String,
    var title: String,
    var userId: Int
){
    constructor(): this(
        0, "", 0, 0, "", "", "", 0
    )
}