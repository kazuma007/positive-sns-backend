package com.example.positivesns.controller

import com.example.positivesns.response.UpdateProcessResponse
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
        @RequestParam("username")
        username: String,

        @RequestParam("password")
        password: String,
    ): UpdateProcessResponse {
        userService.insertUser(username, password)
        return UpdateProcessResponse(result = "success")
    }
}
