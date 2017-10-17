package com.demo.service.impl

import com.demo.base.UserType
import com.demo.entity.User
import com.demo.repo.UserRepo
import com.demo.service.UserService
import com.demo.util.RecordBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(propagation = Propagation.REQUIRED)
class UserServiceImpl
@Autowired
constructor(
        private val userRepo: UserRepo,
        private val passwordEncoder: PasswordEncoder
) : UserService {

    @Transactional(readOnly = true)
    override fun findById(id: Long): User {
        return userRepo.findOne(id)
    }

    @Transactional(readOnly = true)
    override fun countUsers(): Long {
        return userRepo.countUsers()
    }

    @Transactional(readOnly = true)
    override fun findAll(): List<User> {
        return userRepo.findAll()
    }

    override fun create(email: String, password: String, userType: UserType): User {
        val user = RecordBuilder.buildUser(email)
        user.password = password
        user.userType = userType
        return create(user)
    }

    override fun create(user: User): User {
        if (user.password != null) {
            user.password = passwordEncoder.encode(user.password)
        }
        return userRepo.save(user)
    }

    @Secured("ROLE_ADMIN")
    @PreAuthorize("#user.username ne authentication.principal.username")
    override fun remove(user: User) {
        userRepo.delete(user)
    }

    @Secured("ROLE_ADMIN")
    @PreAuthorize("authentication.principal.username ne @userServiceImpl.findById(#id).username")
    override fun remove(id: Long) {
        userRepo.delete(id)
    }
}
