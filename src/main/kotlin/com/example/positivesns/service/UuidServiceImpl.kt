package com.example.positivesns.service

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UuidServiceImpl : UuidService {
    override fun getUuid(): String {
        return UUID.randomUUID().toString()
    }
}
