package com.example.proyectofinal.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinal.R
import com.example.proyectofinal.adaptadores.GastoAdaptador
import com.example.proyectofinal.databinding.FragmentoGastosBinding
import com.example.proyectofinal.modelos.Actividad

class FragmentoGastos : Fragment() {

    private lateinit var actividadSeleccionada: Actividad
    private lateinit var gastoAdaptador: GastoAdaptador

    companion object {
        fun nuevaInstancia(actividad: Actividad): FragmentoGastos {
            val fragment = FragmentoGastos()
            val args = Bundle()
            args.putSerializable("actividad", actividad)  // Pasamos la actividad como argumento
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.fragmento_gastos, container, false)

        val binding = FragmentoGastosBinding.bind(vista)

        // Recuperamos la actividad pasada como argumento
        actividadSeleccionada = arguments?.getSerializable("actividad") as Actividad

        // Distribuimos los gastos y actualizamos los balances
        for (gasto in actividadSeleccionada.gastos) {
            gasto.distribuirGasto()  // Distribuye el gasto y actualiza los balances
        }

        // Configuramos el RecyclerView para mostrar los gastos
        gastoAdaptador = GastoAdaptador(actividadSeleccionada.gastos)
        binding.rvGastos.layoutManager = LinearLayoutManager(context)
        binding.rvGastos.adapter = gastoAdaptador

        return vista
    }
}
