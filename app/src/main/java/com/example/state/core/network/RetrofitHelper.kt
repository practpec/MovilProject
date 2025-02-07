package com.example.state.core.network

import com.example.state.register.data.datasource.RegisterService
import com.example.state.login.data.datasource.LoginService
import com.example.state.tasks.data.datasource.TasksService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private const val BASE_URL = "http://52.7.52.138:8080/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Servicio para registro
    val registerService: RegisterService by lazy {
        retrofit.create(RegisterService::class.java)
    }

    // Servicio para login
    val loginService: LoginService by lazy {
        retrofit.create(LoginService::class.java)
    }

    // Servicio para tareas
    val tasksService: TasksService by lazy {
        retrofit.create(TasksService::class.java)
    }
}
