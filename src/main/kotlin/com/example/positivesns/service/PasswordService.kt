package com.example.positivesns.service

interface PasswordService {
    fun createHashedPassword(password: String) : String
    fun isSamePassword(password: String, hashedPassword: String) : Boolean
}
