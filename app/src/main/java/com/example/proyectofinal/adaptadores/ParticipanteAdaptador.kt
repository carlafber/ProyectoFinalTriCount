package com.example.proyectofinal.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.modelos.Participante

class ParticipanteAdaptador(
    private val participantes: MutableList<Participante>,
    private val onEliminar: (Participante) -> Unit
) : RecyclerView.Adapter<ParticipanteAdaptador.ParticipanteViewHolder>() {

    class ParticipanteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.tvNombreParticipante)
        val txtEmail: TextView = view.findViewById(R.id.tvEmailParticipante)
        val btEliminar: ImageButton = view.findViewById(R.id.ibtBorrarParticipante)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipanteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.elemeto_participante, parent, false)
        return ParticipanteViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParticipanteViewHolder, position: Int) {
        val participante = participantes[position]
        holder.txtNombre.text = participante.nombre
        holder.txtEmail.text = participante.email
        holder.btEliminar.setOnClickListener {
            onEliminar(participante)
        }
    }

    override fun getItemCount() = participantes.size
}
