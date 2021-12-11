package com.example.positivesns.service

import com.example.positivesns.model.dynamo.User
import com.example.positivesns.repository.UserRepository
import com.example.positivesns.response.user.UserResponse
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@Service
class UserServiceImpl(
    private val datetimeService: DatetimeService,
    private val passwordService: PasswordService,
    private val userRepository: UserRepository,
) : UserService {
    override fun insertUser(userId: String, username: String, password: String) : Int {
        val user = userRepository.getUser(userId)
        if (user !== null) return -999

        userRepository.insertUser(createUser(userId, username, password))
        return 1
    }

    override fun auth(userId: String, password: String): UserResponse {
        val user = userRepository.getUser(userId) ?: return UserResponse()
        val result = passwordService.isSamePassword(password, user.password)

        // if the password does not match with the one registered in DB
        if (!result) {
            return UserResponse()
        }

        return UserResponse(
            userId = user.userId,
            username = user.username,
            registeredTime = user.registeredTime?.let {
                ZonedDateTime.ofInstant(
                    Instant.ofEpochMilli(it),
                    ZoneId.systemDefault()
                )
            },
            updatedTime = user.updatedTime?.let {
                ZonedDateTime.ofInstant(
                    Instant.ofEpochMilli(it),
                    ZoneId.systemDefault()
                )
            },
        )
    }

    override fun deleteUser(userId: String) {
        TODO("Not yet implemented")
    }

    override fun updateUser(userId: String, username: String, password: String) {
        TODO("Not yet implemented")
    }

    fun createUser(userId: String, username: String, password: String): User {
        val datetime = datetimeService.getCurrentTime()
        val hashedPassword = passwordService.createHashedPassword(password)
        return User(
            userId = userId,
            username = username,
            password = hashedPassword,
            registeredTime = datetime.toInstant().toEpochMilli(),
            updatedTime = datetime.toInstant().toEpochMilli(),
        )
    }
}
