package com.example.stunthink.database.user

data class UserState(
    val user: User = User(""),
    val token: String = ""
)