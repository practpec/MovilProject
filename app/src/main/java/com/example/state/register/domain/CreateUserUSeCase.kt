package com.example.state.register.domain

import com.example.state.register.data.model.CreateUserRequest
import com.example.state.register.data.model.UserDTO
import com.example.state.register.data.model.UsernameValidateDTO
import com.example.state.register.data.repository.RegisterRepository

class CreateUserUSeCase {
    private  val repository = RegisterRepository()

    suspend operator fun invoke(user: CreateUserRequest) : Result<UserDTO> {
        return repository.createUser(user)
    }
}