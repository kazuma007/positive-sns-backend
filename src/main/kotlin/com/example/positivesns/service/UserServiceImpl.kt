package com.example.positivesns.service

import com.example.positivesns.model.dynamo.User
import com.example.positivesns.repository.UserRepository
import com.example.positivesns.response.post.PostListResponse
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val datetimeService: DatetimeService,
    private val uuidService: UuidService,
    private val passwordService: PasswordService,
    private val userRepository: UserRepository,
) : UserService {
    override fun insertUser(username: String, password: String) {
        val user = createUser(username, password)
        userRepository.insertUser(user)
    }

    override fun auth(username: String, password: String): List<PostListResponse> {
        TODO("Not yet implemented")
    }

    override fun deleteUser(userId: String) {
        TODO("Not yet implemented")
    }

    override fun updateUser(userId: String, username: String, password: String) {
        TODO("Not yet implemented")
    }

    fun createUser(username: String, password: String): User {
        val datetime = datetimeService.getCurrentTime()
        val hashedPassword = passwordService.createHashedPassword(password)
        return User(
            userId = uuidService.getUuid().replace("-", ""),
            username = username,
            password = hashedPassword,
            registeredTime = datetime.toInstant().toEpochMilli(),
            updatedTime = datetime.toInstant().toEpochMilli(),
        )
    }
}