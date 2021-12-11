package com.example.positivesns.service

import at.favre.lib.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class PasswordServiceImpl : PasswordService {
    override fun createHashedPassword(password: String) : String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    override fun isSamePassword(password: String, hashedPassword: String): Boolean {
        val result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword)
        return result.verified
    }
}
