package com.example.state.register.domain

import com.example.state.register.data.model.UsernameValidateDTO
import com.example.state.register.data.repository.RegisterRepository

class UsernameValidateUseCase {
    private  val repository = RegisterRepository()

    suspend operator fun invoke() : Result<UsernameValidateDTO> {
        return repository.validateUsername()
    }
}