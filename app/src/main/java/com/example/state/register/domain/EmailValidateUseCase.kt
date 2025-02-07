package com.example.state.register.domain

import com.example.state.register.data.model.EmailValidateDTO
import com.example.state.register.data.repository.RegisterRepository

class EmailValidateUseCase {

    private val repository = RegisterRepository()

    suspend operator fun invoke(email: String): Result<EmailValidateDTO> {
        val result = repository.validateEmail(email)

        return result
    }
}
