package com.example.practica2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.practica2.databinding.ActivityLoginBinding
import com.example.practica2.network.RetrofitClient
import com.example.practica2.network.models.LoginRequest
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("datos_usuario", MODE_PRIVATE)

        binding.btnLogin.setOnClickListener {
            val correo = binding.etCorreo.text.toString()
            val password = binding.etPassword.text.toString()

            if (correo.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val loginRequest = LoginRequest(correo, password)

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.apiService.login(loginRequest)
                    if (response.isSuccessful) {
                        val user = response.body()?.usuario

                        if (user == null) {
                            Toast.makeText(this@LoginActivity, "Usuario no recibido", Toast.LENGTH_SHORT).show()
                            return@launch
                        }

                        prefs.edit().apply {
                            putString("nombre", user.nombre)
                            putString("rol", user.rol)
                            apply()
                        }

                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Error: ${response.errorBody()?.string()}", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, "Error de red: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.btnIrRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }
}
