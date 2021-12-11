package com.example.positivesns.response.reply

import java.time.ZonedDateTime

data class ReplyResponse(
    var replyId: String = "",
    var postId: String = "",
    var text: String = "",
    var registeredTime: ZonedDateTime? = null,
    var updatedTime: ZonedDateTime? = null,
)
