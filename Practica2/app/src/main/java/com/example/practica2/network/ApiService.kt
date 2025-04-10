package com.example.practica2.network

import com.example.practica2.network.models.LoginRequest
import com.example.practica2.network.models.RegisterRequest
import com.example.practica2.network.models.UserResponse
import com.example.practica2.network.models.Usuario

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<Map<String, String>>

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<UserResponse>
    @GET("usuarios")
    suspend fun getUsers(): Response<List<Usuario>>


    @PUT("usuarios/{id}")
    suspend fun actualizarUsuario(@Path("id") id: String, @Body usuario: Usuario): Response<Unit>

    @DELETE("usuarios/{id}")
    suspend fun deleteUser(@Path("id") id: String): Response<Unit>

}
