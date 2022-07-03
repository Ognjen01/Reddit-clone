package com.ognjenlazic.reddit.model

import java.util.*

data class Banned(
    val timestamp: Date,
    val userId: Long,
    val communityId: Long
)