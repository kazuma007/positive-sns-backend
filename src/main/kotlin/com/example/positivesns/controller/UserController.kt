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
        if (result) {
            return UpdateProcessResponse(result = "success")
        }
        return UpdateProcessResponse(result = "fail")
    }

    @GetMapping(
        path = ["auth"]
    )
    fun auth(
        @RequestParam("userId")
        userId: String,

        @RequestParam("password")
        password: String,
    ): UserResponse {
        return userService.auth(userId, password)
    }

    @GetMapping(
        path = ["update"]
    )
    fun updateUser(
        @RequestParam("userId")
        userId: String,

        @RequestParam("username")
        username: String?,

        @RequestParam("currentPassword")
        currentPassword: String,

        @RequestParam("newPassword")
        newPassword: String,
    ): UserResponse {
        return userService.updateUser(userId, username, currentPassword, newPassword)
    }

    @GetMapping(
        path = ["delete"]
    )
    fun deleteUser(
        @RequestParam("userId")
        userId: String,

        @RequestParam("password")
        password: String,
    ): UpdateProcessResponse {
        val result = userService.deleteUser(userId, password)
        if (result) {
            return UpdateProcessResponse(result = "success")
        }
        return UpdateProcessResponse(result = "fail")
    }
}
