package com.example.positivesns.infrastructure.dynamo

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.example.positivesns.model.dynamo.Reply
import com.example.positivesns.repository.ReplyRepository
import org.springframework.stereotype.Repository

@Repository
class ReplyRepositoryImpl : ReplyRepository {
    override fun insertReply(reply: Reply) {
        mapper.save(reply)
    }

    override fun deleteReply(reply: Reply) {
        mapper.delete(reply)
    }

    override fun updateReply(reply: Reply) {
        mapper.save(reply, DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES.config())
    }

    override fun getReplies(): List<Reply> {
        val scanExpression = DynamoDBScanExpression()
        return mapper.parallelScan(Reply::class.java, scanExpression, 2)
    }

    override fun getReplies(userId: String): List<Reply> {
        val eav = HashMap<String, AttributeValue>()
        eav[":userId"] = AttributeValue().withS(userId)

        val scanExpression = DynamoDBScanExpression()
            .withFilterExpression("user_id = :userId")
            .withExpressionAttributeValues(eav)

        return mapper.parallelScan(Reply::class.java, scanExpression, 2)
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