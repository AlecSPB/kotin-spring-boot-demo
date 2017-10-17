package com.demo.repo

import com.demo.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepo : JpaRepository<User, Long> {

    @Query("select u from User u where u.username like %?1%")
    fun findAllByUserName(username: String): List<User>

    @Query("select u from User u where u.username= :un")
    fun findOneByUsername(@Param("un") username: String): User

    @Query("select u.username from User u where u.id= :id")
    fun findUsernameById(id: Long?): String

    @Query("select count(u) from User u")
    fun countUsers(): Long
}
