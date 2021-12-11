package com.example.positivesns.service

import com.example.positivesns.model.dynamo.Post
import com.example.positivesns.repository.PostRepository
import com.example.positivesns.response.post.PostGetResponse
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@Service
class PostServiceImpl(
    private val datetimeService: DatetimeService,
    private val postRepository: PostRepository,
    private val uuidService: UuidService,
) : PostService {
    override fun insertPost(
        userId: String,
        text: String,
    ) {
        val post = createPost(userId, text)
        postRepository.insertPost(post)
    }

    override fun getPosts(
        userId: String?,
    ): List<PostGetResponse> {
        val posts = if (userId.isNullOrBlank()) {
            postRepository.getPosts()
        } else {
            postRepository.getPosts(userId)
        }

        if (posts.isEmpty()) {
            return emptyList()
        }

        val postResponseList = posts.map { post: Post ->
            PostGetResponse(
                userId = post.userId,
                postId = post.postId,
                text = post.text,
                registeredTime = ZonedDateTime.ofInstant(
                    Instant.ofEpochMilli(post.registeredTime),
                    ZoneId.systemDefault()
                ),
                updatedTime = ZonedDateTime.ofInstant(
                    Instant.ofEpochMilli(post.updatedTime),
                    ZoneId.systemDefault()
                ),
            )
        }

        return postResponseList.sortedByDescending { it.registeredTime }
    }

    fun createPost(
        userId: String,
        text: String,
    ): Post {
        val datetime = datetimeService.getCurrentTime()
        return Post(
            userId = userId,
            postId = uuidService.getUuid().replace("-", ""),
            text = text,
            registeredTime = datetime.toInstant().toEpochMilli(),
            updatedTime = datetime.toInstant().toEpochMilli(),
        )
    }
}