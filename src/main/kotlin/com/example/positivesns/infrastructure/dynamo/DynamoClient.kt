package com.example.positivesns.infrastructure.dynamo

interface DynamoClient {
    fun <T> save(t: T)
}