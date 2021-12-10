package com.example.positivesns.infrastructure.dynamo

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.example.positivesns.model.dynamo.Post
import com.example.positivesns.repository.PostRepository
import org.springframework.stereotype.Repository

@Repository
class PostRepositoryImpl : PostRepository {
    override fun insertPost(post: Post) {
        mapper.save(post)
    }

    companion object {
        private val CLIENT = AmazonDynamoDBClientBuilder
            .standard()
            .withEndpointConfiguration(
                AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "ap-northeast-1")
            )
            .build()
        private val mapper = DynamoDBMapper(CLIENT)
    }
}