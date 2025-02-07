package com.example.state.register.data.datasource

import com.example.state.register.data.model.CreateUserRequest
import com.example.state.register.data.model.UserDTO
import com.example.state.register.data.model.EmailValidateDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RegisterService {

    @GET("user/validate/{email}")
    suspend fun validateEmail(@Path("email") email: String): Response<EmailValidateDTO>

    @POST("user/register")
    suspend fun createUser(@Body request: CreateUserRequest): Response<UserDTO>
}
