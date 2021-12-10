package com.example.positivesns.service

import java.time.ZonedDateTime

interface DatetimeService {
    fun getCurrentTime(): ZonedDateTime
}