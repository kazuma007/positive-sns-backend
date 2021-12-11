package com.example.positivesns.infrastructure.dynamo

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.example.positivesns.model.dynamo.User
import com.example.positivesns.repository.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl : UserRepository {
    override fun insertUser(user: User) {
        mapper.save(user)
    }

    override fun deleteUser(user: User) {
        mapper.delete(user)
    }

    override fun updateUser(user: User) {
        mapper.save(user, DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES.config())
    }

    override fun getUser(userId: String): User? {
        return mapper.load(User::class.java, userId)
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
