package com.example.positivesns.response.post

import java.time.ZonedDateTime

data class PostGetResponse(
    var userId: String = "",
    var postId: String = "",
    var text: String = "",
    var registeredTime: ZonedDateTime? = null,
    var updatedTime: ZonedDateTime? = null,
)
