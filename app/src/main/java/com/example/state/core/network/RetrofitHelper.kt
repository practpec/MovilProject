package com.example.state.core.network

import com.example.state.register.data.datasource.RegisterApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private const val BASE_URL = "https://run.mocky.io/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRetrofit() : RegisterApi {
        return retrofit.create(RegisterApi::class.java)
    }
}