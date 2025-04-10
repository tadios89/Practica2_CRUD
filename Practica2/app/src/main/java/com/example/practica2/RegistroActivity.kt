package com.example.practica2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.practica2.databinding.ActivityRegistroBinding
import com.example.practica2.network.RetrofitClient
import com.example.practica2.network.models.RegisterRequest
import kotlinx.coroutines.launch

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistrar.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            val correo = binding.etCorreo.text.toString()
            val password = binding.etPassword.text.toString()
            val rol = "usuario" // o "admin" si quieres probar

            // Validación básica
            if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = RegisterRequest(nombre, correo, password, rol)

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.apiService.register(request)
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegistroActivity, "✅ Registro exitoso", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        val errorMsg = response.errorBody()?.string()
                        Log.e("Registro", "❌ Error body: $errorMsg")
                        Toast.makeText(this@RegistroActivity, "❌ Error: ${response.errorBody()?.string()}", Toast.LENGTH_LONG).show()

                    }
                } catch (e: Exception) {
                    Toast.makeText(this@RegistroActivity, "⚠️ Error de red: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        } // ← cierre del setOnClickListener
    } // ← cierre del onCreate
}
