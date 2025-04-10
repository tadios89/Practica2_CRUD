package com.example.practica2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("datos_usuario", MODE_PRIVATE)
        val nombre = prefs.getString("nombre", null)

        if (nombre != null) {
            // Si ya hay sesión guardada, ir a MainActivity
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            // Si no hay sesión, ir al Login
            startActivity(Intent(this, LoginActivity::class.java))
        }

        finish()
    }
}
