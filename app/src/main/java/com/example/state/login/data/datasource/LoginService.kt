package com.example.state.login.data.datasource

import com.example.state.login.data.model.LoginRequest
import com.example.state.login.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("user/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}
