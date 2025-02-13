package com.example.proyectofinal.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.databinding.ElementoGastoBinding
import com.example.proyectofinal.modelos.Gasto

class GastoAdaptador(private val gastos: List<Gasto>) : RecyclerView.Adapter<GastoAdaptador.GastoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GastoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.elemento_gasto, parent, false)
        return GastoViewHolder(view)
    }

    override fun onBindViewHolder(holder: GastoViewHolder, position: Int) {
        val gasto = gastos[position]
        holder.bind(gasto)
    }

    override fun getItemCount(): Int = gastos.size

    class GastoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ElementoGastoBinding.bind(itemView)

        fun bind(gasto: Gasto) {
            binding.txtNombreGasto.text = gasto.nombre
            binding.txtPrecioGasto.text = "${gasto.precio}€"

            // Mostrar los participantes que deben este gasto
            binding.txtPagador.text = "Pagado por: ${gasto.pagador.nombre}"
        }
    }
}
