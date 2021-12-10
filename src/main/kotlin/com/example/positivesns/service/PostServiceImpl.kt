package com.example.positivesns.service

import com.example.positivesns.model.dynamo.Post
import com.example.positivesns.repository.PostRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
) : PostService {
    override fun insertPost() {
        val datetime = LocalDateTime.now().atZone(ZoneId.systemDefault())
        val post = Post(
            userId = "sample-user-1",
            postId = UUID.randomUUID().toString().replace("-", ""),
            text = "sample test",
            registeredTime = datetime.toInstant().toEpochMilli(),
            updatedTime = datetime.toInstant().toEpochMilli(),
        )
        postRepository.insertPost(post)
    }
}