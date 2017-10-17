package com.demo.service

import com.demo.base.UserType
import com.demo.entity.User

interface UserService {

    fun findById(id: Long): User

    fun countUsers(): Long

    fun create(email: String, password: String, userType: UserType): User

    fun create(user: User): User

    fun remove(user: User)

    fun remove(id: Long)

    fun findAll(): List<User>
}
