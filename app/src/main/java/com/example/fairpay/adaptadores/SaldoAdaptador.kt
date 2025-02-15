package com.example.fairpay.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fairpay.R
import com.example.fairpay.databinding.ElementoSaldoBinding
import com.example.fairpay.modelos.Participante

class SaldoAdaptador(private val participantes: List<Participante>) : RecyclerView.Adapter<SaldoAdaptador.DeudaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeudaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.elemento_saldo, parent, false)
        return DeudaViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeudaViewHolder, position: Int) {
        val participante = participantes[position]
        holder.bind(participante)
    }

    override fun getItemCount(): Int = participantes.size

    class DeudaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ElementoSaldoBinding.bind(itemView)


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
                binding.txtDeuda.text = String.format("%.2f€", participante.balance)
            }
        }
    }
}
