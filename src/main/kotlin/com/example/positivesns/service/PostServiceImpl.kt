package com.example.positivesns.service

import com.example.positivesns.model.dynamo.Post
import com.example.positivesns.repository.PostRepository
import com.example.positivesns.response.post.PostListResponse
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
    ): List<PostListResponse> {
        val posts = if (userId.isNullOrBlank()) {
            postRepository.getPosts()
        } else {
            postRepository.getPosts(userId)
        }

        if (posts.isEmpty()) {
            return emptyList()
        }

        return posts.map { post: Post ->
            PostListResponse(
                userId = post.userId,
                postId = post.postId,
                text = post.text,
                registeredTime = post.registeredTime?.let {
                    ZonedDateTime.ofInstant(
                        Instant.ofEpochMilli(it),
                        ZoneId.systemDefault()
                    )
                },
                updatedTime = post.updatedTime?.let {
                    ZonedDateTime.ofInstant(
                        Instant.ofEpochMilli(it),
                        ZoneId.systemDefault()
                    )
                },
            )
        }.sortedByDescending { it.registeredTime }
    }

    override fun deletePost(postId: String) {
        val post = Post(
            postId = postId,
        )
        postRepository.deletePost(post)
    }

    override fun updatePost(postId: String, text: String) {
        val post = Post(
            postId = postId,
            text = text,
        )
        postRepository.updatePost(post)
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