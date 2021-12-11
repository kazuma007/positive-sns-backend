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
    override fun insertUser(userId: String, username: String, password: String) : Boolean {
        val user = userRepository.getUser(userId)
        if (user !== null) return false

        userRepository.insertUser(createUser(userId, username, password))
        return true
    }

    override fun auth(userId: String, password: String): UserResponse {
        val user = userRepository.getUser(userId) ?: return UserResponse()
        val result = user.password?.let { passwordService.isSamePassword(password, it) }

        // if the password does not match with the one registered in DB
        if ((result == null) || !result) {
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

    override fun deleteUser(userId: String, password: String) : Boolean {
        val user = userRepository.getUser(userId) ?: return false
        val result = user.password?.let { passwordService.isSamePassword(password, it) }

        // if the password does not match with the one registered in DB
        if ((result == null) || !result) {
            return false
        }

        val deleteToUser = User(
            userId = userId,
        )
        userRepository.deleteUser(deleteToUser)
        return true
    }

    override fun updateUser(userId: String,
                            username: String?,
                            currentPassword: String,
                            newPassword: String?,
    ): UserResponse {
        val user = userRepository.getUser(userId) ?: return UserResponse()
        val result = user.password?.let { passwordService.isSamePassword(currentPassword, it) }

        // if the password does not match with the one registered in DB
        if ((result == null) || !result) {
            return UserResponse()
        }

        val updatedUser = createUpdatedUser(userId, username, newPassword)
        userRepository.updateUser(updatedUser)
        return UserResponse(
            userId = updatedUser.userId,
            username = updatedUser.username,
            registeredTime = updatedUser.registeredTime?.let {
                ZonedDateTime.ofInstant(
                    Instant.ofEpochMilli(it),
                    ZoneId.systemDefault()
                )
            },
            updatedTime = updatedUser.updatedTime?.let {
                ZonedDateTime.ofInstant(
                    Instant.ofEpochMilli(it),
                    ZoneId.systemDefault()
                )
            },
        )
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

    fun createUpdatedUser(userId: String, username: String?, newPassword: String?): User {
        val hashedPassword = if (newPassword.isNullOrBlank()) {
            null
        } else {
            passwordService.createHashedPassword(newPassword)
        }
        return User(
            userId = userId,
            username = username,
            password = hashedPassword,
        )
    }
}
