package com.example.positivesns.service

interface PostService {
    fun insertPost(
        userId: String,
        text: String,
    )
}