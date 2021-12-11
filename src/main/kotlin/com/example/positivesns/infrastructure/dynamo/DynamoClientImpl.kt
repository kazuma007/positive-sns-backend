package com.example.positivesns.infrastructure.dynamo

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper

class DynamoClientImpl : DynamoClient {
    override fun <T> save(t: T) {
        mapper.save(t)
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
