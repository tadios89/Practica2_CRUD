package com.example.practica2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practica2.R
import com.example.practica2.network.models.Usuario

class UserAdapter(
    private var usuarios: List<Usuario>,
    private val onDeleteClick: (String) -> Unit,
    private val onEditClick: (Usuario) -> Unit // NUEVO
) : RecyclerView.Adapter<UserAdapter.UsuarioViewHolder>() {

    inner class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvCorreo: TextView = itemView.findViewById(R.id.tvCorreo)
        val tvRol: TextView = itemView.findViewById(R.id.tvRol)
        val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)
        val btnEditar: Button = itemView.findViewById(R.id.btnEditar) // <-- nuevo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_usuario, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = usuarios[position]

        holder.tvNombre.text = usuario.nombre
        holder.tvCorreo.text = usuario.correo
        holder.tvRol.text = usuario.rol

        holder.btnEliminar.setOnClickListener {
            usuario._id?.let { id -> onDeleteClick(id) }
        }
        holder.btnEditar.setOnClickListener {
            usuario._id?.let { onEditClick(usuario) }
        }

    }

    override fun getItemCount(): Int = usuarios.size

    fun actualizarLista(nuevaLista: List<Usuario>) {
        usuarios = nuevaLista
        notifyDataSetChanged()
    }
}
