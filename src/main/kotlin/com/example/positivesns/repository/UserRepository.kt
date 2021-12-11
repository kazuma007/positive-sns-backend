package com.example.positivesns.repository

import com.example.positivesns.model.dynamo.User

interface UserRepository {
    fun insertUser(user: User)
    fun deleteUser(user: User)
    fun updateUser(user: User)
    fun getUser(user: User): User
}
