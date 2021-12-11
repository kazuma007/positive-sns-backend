package com.example.positivesns.service

import com.example.positivesns.response.post.PostListResponse

interface UserService {
    fun insertUser(
        username: String,
        password: String,
    )

    fun auth(
        username: String,
        password: String,
    ): List<PostListResponse>

    fun deleteUser(
        userId: String,
    )

    fun updateUser(
        userId: String,
        username: String,
        password: String,
    )
}
