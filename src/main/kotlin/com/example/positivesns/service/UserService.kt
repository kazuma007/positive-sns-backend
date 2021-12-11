package com.example.positivesns.service

import com.example.positivesns.response.user.UserResponse

interface UserService {
    fun insertUser(
        userId: String,
        username: String,
        password: String,
    ): Boolean

    fun auth(
        userId: String,
        password: String,
    ): UserResponse

    fun deleteUser(
        userId: String,
        password: String,
    ): Boolean

    fun updateUser(
        userId: String,
        username: String?,
        currentPassword: String,
        newPassword: String?,
    ): UserResponse
}
