package com.demo.util

import com.demo.entity.User

object RecordBuilder {

    fun buildUser(email: String): User {
        val user = User()
        user.email = email
        val namePieces = email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        user.username = namePieces[0].replace(".", "").toLowerCase()
        if (namePieces[0].contains(".")) {
            // fn and ln can be inferred
            val names = namePieces[0].split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            user.lastName = names[1]
            user.firstName = names[0]
        }
        user.active = true
        return user
    }
}
