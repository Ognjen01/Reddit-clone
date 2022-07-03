package com.ognjenlazic.reddit.network.model

data class ResetPasswordBody(
    val newPassword: String,
    val oldPassword: String,
    val userId: Int
)