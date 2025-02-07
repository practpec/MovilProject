package com.example.state.tasks.data.datasource

import com.example.state.tasks.data.model.CreateTaskRequest
import com.example.state.tasks.data.model.TaskDTO

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TasksService {

    @GET("tasks/user/{id_user}")
    suspend fun getTasks(@Path("id_user") idUser: String): Response<List<TaskDTO>>

    @DELETE("tasks/{id_task}")
    suspend fun deleteTask(@Path("id_task") taskId: String)

    @PATCH("tasks/{id_task}/complete")
    suspend fun toggleTaskCompletion(@Path("id_task") taskId: String): TaskDTO

    @POST("tasks")//responseTaskDTO
    suspend fun createTask(@Body request: CreateTaskRequest): Response<TaskDTO>

}
