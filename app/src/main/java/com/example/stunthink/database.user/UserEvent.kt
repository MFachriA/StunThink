package com.example.stunthink.database.user

sealed interface UserEvent {
    object SaveUser: UserEvent
    data class SetToken(val token: String): UserEvent
    data class DeleteUser(val user: User): UserEvent
}