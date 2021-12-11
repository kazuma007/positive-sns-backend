package com.example.positivesns.service

import com.example.positivesns.response.post.PostListResponse

interface PostService {
    fun insertPost(
        userId: String,
        text: String,
    )

    fun getPosts(
        userId: String?,
    ): List<PostListResponse>

    fun deletePost(
        postId: String,
    )

    fun updatePost(
        postId: String,
        text: String,
    )
}
