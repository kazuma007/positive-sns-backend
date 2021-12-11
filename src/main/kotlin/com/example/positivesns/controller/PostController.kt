package com.example.positivesns.controller

import com.example.positivesns.response.post.PostListResponse
import com.example.positivesns.response.post.PostResultResponse
import com.example.positivesns.service.PostService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    path = ["v1/post"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class PostController(
    private val postService: PostService
) {
    // TODO PostMappingに変更する
    @GetMapping(
        path = ["insert"]
    )
    fun insertPost(
        @RequestParam("userId")
        userId: String,

        @RequestParam("text")
        text: String,
    ): PostResultResponse {
        postService.insertPost(userId, text)
        return PostResultResponse(result = "success")
    }

    @GetMapping(
        path = ["get"]
    )
    fun getPosts(
        @RequestParam("userId")
        userId: String?,
    ): List<PostListResponse> {
        return postService.getPosts(userId)
    }

    @GetMapping(
        path = ["delete"]
    )
    fun deletePost(
        @RequestParam("postId")
        postId: String,
    ): PostResultResponse {
        postService.deletePost(postId)
        return PostResultResponse(result = "success")
    }

    @GetMapping(
        path = ["update"]
    )
    fun updatePost(
        @RequestParam("text")
        text: String,

        @RequestParam("postId")
        postId: String,
    ): PostResultResponse {
        postService.updatePost(postId, text)
        return PostResultResponse(result = "success")
    }
}
