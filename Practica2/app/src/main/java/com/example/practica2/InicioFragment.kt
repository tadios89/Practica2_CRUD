package com.example.practica2

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class InicioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val texto = TextView(requireContext())
        texto.text = "🟢 Bienvenido a la app"
        texto.textSize = 24f
        texto.gravity = Gravity.CENTER
        return texto
    }
}

