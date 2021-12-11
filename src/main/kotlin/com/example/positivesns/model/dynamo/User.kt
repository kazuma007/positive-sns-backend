package com.example.positivesns.model.dynamo

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable

@DynamoDBTable(tableName = "user")
data class User(
    @DynamoDBHashKey(attributeName = "user_id")
    var userId: String = "",

    @DynamoDBAttribute(attributeName = "username")
    var username: String? = null,

    @DynamoDBAttribute(attributeName = "password")
    var password: String? = null,

    @DynamoDBAttribute(attributeName = "registered_time")
    var registeredTime: Long? = null,

    @DynamoDBAttribute(attributeName = "updated_time")
    var updatedTime: Long? = null,
)
