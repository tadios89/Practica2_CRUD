package com.example.practica2.network.models

data class RegisterRequest(
    val nombre: String,
    val correo: String,
    val password: String,
    val rol: String
)
