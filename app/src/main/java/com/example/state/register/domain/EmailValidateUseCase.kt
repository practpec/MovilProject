package com.example.state.register.domain

import com.example.state.register.data.model.EmailValidateDTO  // Cambiado a EmailValidateDTO
import com.example.state.register.data.repository.RegisterRepository

class EmailValidateUseCase {  // Cambiado a EmailValidateUseCase

    private val repository = RegisterRepository()

    suspend operator fun invoke(email: String): Result<EmailValidateDTO> {  // Cambiado a aceptar email
        val result = repository.validateEmail(email)  // Cambiado a validateEmail

        // En caso de existir acá debe estar la lógica de negocio
        return result
    }
}
