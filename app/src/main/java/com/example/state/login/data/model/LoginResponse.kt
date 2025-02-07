package com.example.state.login.data.model

data class LoginResponse(
    val token: String,
    val user: User
)

data class User(
    val id_user: Int,
    val username: String,
    val email: String,
    val password: String
)
