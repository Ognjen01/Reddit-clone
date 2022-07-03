package com.ognjenlazic.reddit.network.model

data class UserResponse(
    val avatar: String,
    val description: String,
    val displayName: String,
    val email: String,
    val password: String,
    val registrationDate: List<Int>,
    val userId: Int,
    val userType: String,
    val username: String
){
    constructor(): this(
        "", "","","","", emptyList(),0,"", "",
    )
}