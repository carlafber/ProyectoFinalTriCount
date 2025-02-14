package com.example.proyectofinal.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.databinding.ElementoDeudaBinding
import com.example.proyectofinal.modelos.Participante

class DeudaAdaptador(private val participantes: List<Participante>) : RecyclerView.Adapter<DeudaAdaptador.DeudaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeudaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.elemento_deuda, parent, false)
        return DeudaViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeudaViewHolder, position: Int) {
        val participante = participantes[position]
        holder.bind(participante)
    }

    override fun getItemCount(): Int = participantes.size

    class DeudaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ElementoDeudaBinding.bind(itemView)


        fun bind(participante: Participante) {

            binding.txtParticipante.text = participante.nombre
            // Si el balance es positivo, significa que es un acreedor
            if (participante.balance > 0) {
                binding.txtDeuda.setTextColor(ContextCompat.getColor(itemView.context, R.color.positivo))
                binding.txtDeuda.text = String.format("+%.2f€", participante.balance)
            } else if (participante.balance < 0) {
                // Si el balance es negativo, significa que es un deudor
                binding.txtDeuda.setTextColor(ContextCompat.getColor(itemView.context, R.color.negativo))
                binding.txtDeuda.text = String.format("%.2f€", participante.balance)

            } else {
                binding.txtDeuda.text = "No debe"
            }
        }
    }
}
