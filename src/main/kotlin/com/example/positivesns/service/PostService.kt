package com.example.positivesns.service

import com.example.positivesns.response.post.PostGetResponse

interface PostService {
    fun insertPost(
        userId: String,
        text: String,
    )

    fun getPosts(
        userId: String?,
    ): List<PostGetResponse>
}