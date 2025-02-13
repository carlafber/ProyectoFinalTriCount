package com.example.proyectofinal.adaptadores

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.databinding.ElementoDeudaBinding
import com.example.proyectofinal.modelos.Actividad
import com.example.proyectofinal.modelos.Participante

class DeudaAdaptador(private val actividad: Actividad) : RecyclerView.Adapter<DeudaAdaptador.DeudaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeudaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.elemento_deuda, parent, false)
        return DeudaViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeudaViewHolder, position: Int) {
        val deuda = actividad.deudas[position] // Accedemos a la lista de deudas
        holder.bind(deuda)
    }

    override fun getItemCount(): Int {
        Log.i("Deudas", "Número de deudas: ${actividad.deudas.size}")
        return actividad.deudas.size
    }

    class DeudaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ElementoDeudaBinding.bind(itemView)

        fun bind(deuda: Triple<Participante, Double, Participante>) {
            val (deudor, cantidad, acreedor) = deuda

            Log.i("Deuda", "${deudor.nombre} debe ${cantidad}€ a ${acreedor.nombre}")

            binding.txtParticipante.text = "${deudor.nombre} → ${acreedor.nombre}"
            binding.txtBalance.text = "Cantidad: ${cantidad}€"
            binding.txtDeuda.text = "${deudor.nombre} debe pagar a ${acreedor.nombre}"
        }
    }
}
