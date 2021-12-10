package com.example.positivesns.controller

import com.example.positivesns.response.post.InsertPostResponse
import com.example.positivesns.service.PostService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    path = ["v1/post"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class PostController(
    private val postService: PostService
) {

    @GetMapping
    fun insertPost(): InsertPostResponse {
        postService.insertPost()
        return InsertPostResponse(result = "success")
    }

}