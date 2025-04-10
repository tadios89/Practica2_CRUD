package com.example.practica2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.practica2.databinding.FragmentPerfilBinding

class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // ✅ USAMOS EL MISMO nombre de SharedPreferences que en LoginActivity
        val prefs = requireContext().getSharedPreferences("datos_usuario", Context.MODE_PRIVATE)
        val nombre = prefs.getString("nombre", "Desconocido")
        val rol = prefs.getString("rol", "usuario")

        binding.txtNombre.text = "👤 Nombre: $nombre"
        binding.txtRol.text = "🔐 Rol: $rol"

        binding.btnCerrarSesion.setOnClickListener {
            prefs.edit().clear().apply()
            Toast.makeText(requireContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
