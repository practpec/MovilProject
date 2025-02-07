package com.example.state.register.data.repository

import com.example.state.core.network.RetrofitHelper
import com.example.state.register.data.model.CreateUserRequest
import com.example.state.register.data.model.UserDTO
import com.example.state.register.data.model.EmailValidateDTO  // Cambiado a EmailValidateDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterRepository {

    private val registerService = RetrofitHelper.registerService

    suspend fun validateEmail(email: String): Result<EmailValidateDTO> {  // Cambiado a validateEmail
        return try {
            val response = registerService.validateEmail(email)  // Cambiado a validateEmail
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createUser(request: CreateUserRequest): Result<UserDTO> {
        return try {
            val response = registerService.createUser(request)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
