package com.example.positivesns.service

import com.example.positivesns.model.dynamo.User
import com.example.positivesns.repository.UserRepository
import com.example.positivesns.response.user.UserResponse
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.ZoneId
import java.time.ZonedDateTime

@ExtendWith(MockKExtension::class)
internal class UserServiceImplTest {
    @InjectMockKs
    private lateinit var userService: UserServiceImpl

    @MockK
    private lateinit var datetimeService: DatetimeService

    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var passwordService: PasswordService

    @Test
    fun insertUser_whenUserIdIsAvailable() {
        every {
            userRepository.getUser("user-id")
        } returns null

        every {
            passwordService.createHashedPassword("password")
        } returns "hashed-password"

        every {
            datetimeService.getCurrentTime()
        } returns ZonedDateTime.of(2021, 12, 11, 6, 5, 20, 0, ZoneId.of("Asia/Tokyo"))

        every {
            userRepository.insertUser(
                User(
                    userId = "user-id",
                    username = "username",
                    password = "password",
                    registeredTime = 1639170320000,
                    updatedTime = 1639170320000,
                )
            )
        } returns Unit

        val actual = userService.insertUser("user-id", "username", "password")
        Assertions.assertTrue(actual)

        verify(exactly = 1) { userRepository.getUser("user-id") }
        verify(exactly = 1) { passwordService.createHashedPassword("password") }
        verify(exactly = 1) { datetimeService.getCurrentTime() }
        verify(exactly = 1) {
            userRepository.insertUser(
                User(
                    userId = "user-id",
                    username = "username",
                    password = "password",
                    registeredTime = 1639170320000,
                    updatedTime = 1639170320000,
                )
            )
        }
        confirmVerified(userRepository)
        confirmVerified(passwordService)
        confirmVerified(datetimeService)
    }

    @Test
    fun insertUser_whenUserIdIsNotAvailable() {
        every {
            userRepository.getUser("user-id")
        } returns User(
            userId = "user-id",
        )

        every {
            passwordService.createHashedPassword("password")
        } returns "hashed-password"

        every {
            datetimeService.getCurrentTime()
        } returns ZonedDateTime.of(2021, 12, 11, 6, 5, 20, 0, ZoneId.of("Asia/Tokyo"))

        every {
            userRepository.insertUser(
                User(
                    userId = "user-id",
                    username = "username",
                    password = "password",
                    registeredTime = 1639170320000,
                    updatedTime = 1639170320000,
                )
            )
        } returns Unit

        val actual = userService.insertUser("user-id", "username", "password")
        Assertions.assertFalse(actual)

        verify(exactly = 1) { userRepository.getUser("user-id") }
        verify(exactly = 1) { passwordService.createHashedPassword("password") }
        verify(exactly = 1) { datetimeService.getCurrentTime() }
        verify(exactly = 1) {
            userRepository.insertUser(
                User(
                    userId = "user-id",
                    username = "username",
                    password = "password",
                    registeredTime = 1639170320000,
                    updatedTime = 1639170320000,
                )
            )
        }
        confirmVerified(userRepository)
        confirmVerified(passwordService)
        confirmVerified(datetimeService)
    }

    @Test
    fun auth_whenUserIdDoesNotExist() {
        every {
            userRepository.getUser("user-id")
        } returns null

        val actual = userService.auth("user-id", "password")

        val expected = UserResponse()

        Assertions.assertEquals(expected, actual)

        verify(exactly = 1) { userRepository.getUser("user-id") }
        confirmVerified(userRepository)
    }

    @Test
    fun auth_whenPasswordDoesNotMatch() {
        every {
            userRepository.getUser("user-id")
        } returns User(
            userId = "user-id",
            password = "hashed-password",
        )

        every {
            passwordService.isSamePassword("password", "hashed-password")
        } returns false

        val actual = userService.auth("user-id", "password")

        val expected = UserResponse()

        Assertions.assertEquals(expected, actual)

        verify(exactly = 1) { userRepository.getUser("user-id") }
        verify(exactly = 1) { passwordService.isSamePassword("password", "hashed-password") }
        confirmVerified(userRepository)
        confirmVerified(passwordService)
    }

    @Test
    fun auth_whenSuccess() {
        every {
            userRepository.getUser("user-id")
        } returns User(
            userId = "user-id",
            username = "username",
            password = "hashed-password",
            registeredTime = 1639170320000,
            updatedTime = 1639170320000,
        )

        every {
            passwordService.isSamePassword("password", "hashed-password")
        } returns true

        val actual = userService.auth("user-id", "password")

        val expected = UserResponse(
            userId = "user-id",
            username = "username",
            registeredTime = ZonedDateTime.of(2021, 12, 11, 6, 5, 20, 0, ZoneId.of("Asia/Tokyo")),
            updatedTime = ZonedDateTime.of(2021, 12, 11, 6, 5, 20, 0, ZoneId.of("Asia/Tokyo")),
        )

        Assertions.assertEquals(expected, actual)

        verify(exactly = 1) { userRepository.getUser("user-id") }
        verify(exactly = 1) { passwordService.isSamePassword("password", "hashed-password") }
        confirmVerified(userRepository)
        confirmVerified(passwordService)
    }
}
