package com.example.positivesns.service

import java.util.UUID

class UuidServiceImpl : UuidService {
    override fun getUuid(): String {
        return UUID.randomUUID().toString()
    }
}