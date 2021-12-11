package com.example.positivesns.response.post

import java.time.ZonedDateTime

data class PostListResponse(
    var userId: String = "",
    var postId: String = "",
    var text: String = "",
    var registeredTime: ZonedDateTime? = null,
    var updatedTime: ZonedDateTime? = null,
)
