package com.example.state.login.domain

import com.example.state.login.data.datasource.LoginService
import com.example.state.login.data.model.LoginRequest
import com.example.state.login.data.model.LoginResponse
import com.example.state.login.data.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginUseCase(private val repository: LoginRepository = LoginRepository()) {

    suspend operator fun invoke(user: LoginRequest): Result<LoginResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val result = repository.login(user)
                // Retorna el resultado del repositorio
                result
            } catch (e: Exception) {
                // En caso de error, devuelve el fallo
                Result.failure(e)
            }
        }
    }
}
