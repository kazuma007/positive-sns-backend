package com.example.positivesns.service

import com.example.positivesns.response.post.PostResponse

interface PostService {
    fun insertPost(
        userId: String,
        text: String,
    )

    fun getPosts(
        userId: String?,
    ): List<PostResponse>

    fun deletePost(
        postId: String,
    )

    fun updatePost(
        postId: String,
        text: String,
    )
}
