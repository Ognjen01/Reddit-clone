package com.ognjenlazic.reddit.model

import java.util.*
import kotlin.reflect.KProperty

data class User (
    var avatar: String,
    var description: String,
    var displayName: String,
    var email: String,
    var password: String,
    var registrationDate: String,
    var userId: Int,
    var userType: String,
    var username: String
){
    constructor(): this(
        "", "","","","","",0,"", "",
    )
}