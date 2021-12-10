package com.example.positivesns.service

import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

@Service
class DatetimeServiceImpl : DatetimeService {
    override fun getCurrentTime() : ZonedDateTime {
        return LocalDateTime.now().atZone(ZoneId.systemDefault())
    }
}