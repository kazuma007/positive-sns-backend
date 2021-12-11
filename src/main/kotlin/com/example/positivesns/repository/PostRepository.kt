package com.example.positivesns.repository

import com.example.positivesns.model.dynamo.Post

interface PostRepository {
    fun insertPost(post: Post)
    fun getPosts(): List<Post>
    fun getPosts(userId: String): List<Post>
}