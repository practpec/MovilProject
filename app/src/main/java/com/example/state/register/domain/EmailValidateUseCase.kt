package com.example.state.register.domain

import com.example.state.register.data.model.EmailValidateDTO
import com.example.state.register.data.repository.RegisterRepository

class UsernameValidateUseCase {
    private  val repository = RegisterRepository()

    suspend operator fun invoke() : Result<EmailValidateDTO> {
        val result  = repository.validateUsername()

        // En caso de existir acá debe estar la lógica de negocio
        return result
    }
}