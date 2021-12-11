package com.example.positivesns.response.user

import java.time.ZonedDateTime

data class UserResponse(
    var userId: String = "",
    var username: String = "",
    var registeredTime: ZonedDateTime? = null,
    var updatedTime: ZonedDateTime? = null,
)
