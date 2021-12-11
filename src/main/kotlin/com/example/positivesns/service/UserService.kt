package com.example.positivesns.service

import com.example.positivesns.response.user.UserResponse

interface UserService {
    fun insertUser(
        userId: String,
        username: String,
        password: String,
    ): Int

    fun auth(
        userId: String,
        password: String,
    ): UserResponse

    fun deleteUser(
        userId: String,
    )

    fun updateUser(
        userId: String,
        username: String,
        password: String,
    )
}
