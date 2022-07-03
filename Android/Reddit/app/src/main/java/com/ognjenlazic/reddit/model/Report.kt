package com.ognjenlazic.reddit.model

import com.ognjenlazic.reddit.network.model.UserResponse
import java.util.*

data class Report(
    var accepted: Boolean,
    var commentId: Int?,
    var postId: Int?,
    var reason: String,
    var reportId: Int,
    var timestamp: String,
    var userId: Int
){
    constructor(): this(
        false, 0, 0, "", 0, "",  0
    )
}