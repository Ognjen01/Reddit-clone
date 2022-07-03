package com.ognjenlazic.reddit.network.response

data class Token(
    val accessToken: String,
    val expiresIn: Int
)