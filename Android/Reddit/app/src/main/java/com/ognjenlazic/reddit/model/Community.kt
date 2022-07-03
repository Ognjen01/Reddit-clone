package com.ognjenlazic.reddit.model

data class Community(
    val communityId: Int,
    val creationDate: Long,
    var description: String,
    val moderatorId: Int,
    var name: String,
    val rules: List<Rule>?,
    val suspended: Boolean,
    val suspendedReason: Any?
){
    constructor(): this(
        0, 0, "", 0, "", emptyList(), false, ""
    )
}