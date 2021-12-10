package com.example.positivesns.service

import com.example.positivesns.model.dynamo.Post
import com.example.positivesns.repository.PostRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PostServiceImpl(
    private val datetimeService: DatetimeService,
    private val postRepository: PostRepository,
) : PostService {
    override fun insertPost(
        userId: String,
        text: String,
    ) {
        val post = createPost(userId, text)
        postRepository.insertPost(post)
    }

    private fun createPost(
        userId: String,
        text: String,
    ): Post {
        val datetime = datetimeService.getCurrentTime()
        return Post(
            userId = userId,
            postId = UUID.randomUUID().toString().replace("-", ""),
            text = text,
            registeredTime = datetime.toInstant().toEpochMilli(),
            updatedTime = datetime.toInstant().toEpochMilli(),
        )
    }
}