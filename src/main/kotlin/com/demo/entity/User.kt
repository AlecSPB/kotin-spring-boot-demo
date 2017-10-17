package com.demo.entity

import com.demo.base.AbstractEntity
import com.demo.base.UserType
import org.hibernate.validator.constraints.NotEmpty

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotNull

@Entity
data class User(
        /**
         * username = email
         */
        @NotEmpty
        @Column(nullable = false, unique = true)
        var username: String? = null,

        @Column(name = "first_name")
        var firstName: String? = null,

        @Column(name = "last_name")
        var lastName: String? = null,

        @NotEmpty
        var password: String? = null,

        @NotNull
        @Enumerated(EnumType.STRING)
        @Column(name = "user_type")
        var userType: UserType? = null,

        var address: String? = null,

        @NotEmpty
        @Column(unique = true)
        var email: String? = null,

        @Column
        var active: Boolean = true
) : AbstractEntity()