package com.example.practica2

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practica2.adapters.UserAdapter
import com.example.practica2.databinding.FragmentCrudBinding
import com.example.practica2.network.RetrofitClient
import com.example.practica2.network.models.UserResponse
import kotlinx.coroutines.launch
import kotlin.collections.emptyList
import com.example.practica2.network.models.Usuario

class CrudFragment : Fragment() {

    private var _binding: FragmentCrudBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrudBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UserAdapter(
            listOf(),
            { userId -> deleteUser(userId) },
            { usuario -> mostrarDialogoEditar(usuario) } // NUEVO
        )

        binding.recyclerViewUsers.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewUsers.adapter = adapter

        cargarUsuarios()
    }
    private fun mostrarDialogoEditar(usuario: Usuario) {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_editar_usuario, null)
        val etNombre = view.findViewById<EditText>(R.id.etEditarNombre)
        val etCorreo = view.findViewById<EditText>(R.id.etEditarCorreo)
        val etRol = view.findViewById<EditText>(R.id.etEditarRol)

        etNombre.setText(usuario.nombre)
        etCorreo.setText(usuario.correo)
        etRol.setText(usuario.rol)

        AlertDialog.Builder(requireContext())
            .setTitle("Editar usuario")
            .setView(view)
            .setPositiveButton("Actualizar") { _, _ ->
                val nuevo = Usuario(usuario._id, etNombre.text.toString(), etCorreo.text.toString(), etRol.text.toString())
                actualizarUsuario(nuevo)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    private fun actualizarUsuario(usuario: Usuario) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.actualizarUsuario(usuario._id ?: "", usuario)
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "✅ Usuario actualizado", Toast.LENGTH_SHORT).show()
                    cargarUsuarios() // recarga la lista
                } else {
                    Toast.makeText(requireContext(), "❌ Error al actualizar", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "⚠️ Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cargarUsuarios() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getUsers()
                if (response.isSuccessful) {
                    val users = response.body() ?: listOf<Usuario>()


                    adapter.actualizarLista(users)

                } else {
                    Toast.makeText(requireContext(), "Error al cargar usuarios", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteUser(userId: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.deleteUser(userId)
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "✅ Usuario eliminado", Toast.LENGTH_SHORT).show()
                    cargarUsuarios()
                } else {
                    Toast.makeText(requireContext(), "❌ Error al eliminar", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error de red", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
