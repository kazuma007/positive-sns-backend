package com.example.positivesns.controller

import com.example.positivesns.response.UpdateProcessResponse
import com.example.positivesns.response.user.UserResponse
import com.example.positivesns.service.UserService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    path = ["v1/user"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class UserController(
    private val userService: UserService
) {
    // TODO PostMappingに変更する
    @GetMapping(
        path = ["create"]
    )
    fun createUser(
        @RequestParam("userId")
        userId: String,

        @RequestParam("username")
        username: String,

        @RequestParam("password")
        password: String,
    ): UpdateProcessResponse {
        val result = userService.insertUser(userId, username, password)
        if (result < 0) return UpdateProcessResponse(result = "fail")
        return UpdateProcessResponse(result = "success")
    }

    @GetMapping(
        path = ["auth"]
    )
    fun auth(
        @RequestParam("userId")
        userId: String,

        @RequestParam("password")
        password: String,
    ): UserResponse? {
        return userService.auth(userId, password)
    }
}
