package com.example.practica2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.practica2.InicioFragment
import com.example.practica2.PerfilFragment
import com.example.practica2.CrudFragment
import androidx.fragment.app.Fragment
import com.example.practica2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recuperar el rol del usuario desde SharedPreferences
        val prefs = getSharedPreferences("user_session", MODE_PRIVATE)
        val rol = prefs.getString("rol", "usuario")

        // Fragmento inicial según el rol
        if (rol == "admin") {
            loadFragment(CrudFragment())
        } else {
            loadFragment(PerfilFragment())
        }

        // Navegación inferior
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> loadFragment(InicioFragment())
                R.id.nav_perfil -> loadFragment(PerfilFragment())
                R.id.nav_crud -> loadFragment(CrudFragment())
            }
            true
        }
    }



    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerFragment, fragment)
            .commit()
    }
}
