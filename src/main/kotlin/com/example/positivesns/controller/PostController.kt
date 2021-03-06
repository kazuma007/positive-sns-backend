package com.example.positivesns.controller

import com.example.positivesns.response.post.PostResponse
import com.example.positivesns.response.UpdateProcessResponse
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
    ): UpdateProcessResponse {
        postService.insertPost(userId, text)
        return UpdateProcessResponse(result = "success")
    }

    @GetMapping(
        path = ["get"]
    )
    fun getPosts(
        @RequestParam("userId")
        userId: String?,
    ): List<PostResponse> {
        return postService.getPosts(userId)
    }

    @GetMapping(
        path = ["delete"]
    )
    fun deletePost(
        @RequestParam("postId")
        postId: String,
    ): UpdateProcessResponse {
        postService.deletePost(postId)
        return UpdateProcessResponse(result = "success")
    }

    @GetMapping(
        path = ["update"]
    )
    fun updatePost(
        @RequestParam("text")
        text: String,

        @RequestParam("postId")
        postId: String,
    ): UpdateProcessResponse {
        postService.updatePost(postId, text)
        return UpdateProcessResponse(result = "success")
    }
}
