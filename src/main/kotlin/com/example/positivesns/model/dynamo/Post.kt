package com.example.positivesns.model.dynamo

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable

@DynamoDBTable(tableName = "post")
data class Post(
    @DynamoDBHashKey(attributeName = "user_id")
    var userId: String = "",

    @DynamoDBRangeKey(attributeName = "post_id")
    var postId: String = "",

    @DynamoDBAttribute(attributeName = "text")
    var text: String = "",

    @DynamoDBAttribute(attributeName = "registered_time")
    var registeredTime: Long = 0,

    @DynamoDBAttribute(attributeName = "updated_time")
    var updatedTime: Long = 0,
)
