package com.ognjenlazic.reddit.network.model

import com.ognjenlazic.reddit.model.Rule

class CommunityBody (
    var communityId: Int,
    var creationDate: String,
    var description: String,
    var moderatorId: Int,
    var name: String,
    var rules: List<Rule>?,
    var suspended: Boolean,
    var suspendedReason: Any?
){
    constructor(): this(
        0, "", "", 0, "", emptyList(), false, ""
    )
}