package com.ognjenlazic.reddit.model

data class Karma(
    val entityId: Int,
    var number: Int
){
    constructor(): this(
        0,0
    )
}