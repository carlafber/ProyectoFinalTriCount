package com.example.fairpay.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fairpay.R
import com.example.fairpay.adaptadores.SaldoAdaptador
import com.example.fairpay.databinding.FragmentoSaldosBinding
import com.example.fairpay.modelos.Actividad

class FragmentoSaldos : Fragment() {

    private lateinit var actividadSeleccionada: Actividad
    private lateinit var saldoAdaptador: SaldoAdaptador

    companion object {
        fun nuevaInstancia(actividad: Actividad): FragmentoSaldos {
            val fragment = FragmentoSaldos()
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
        val vista = inflater.inflate(R.layout.fragmento_saldos, container, false)

        val binding = FragmentoSaldosBinding.bind(vista)
        // Recuperamos la actividad pasada como argumento
        actividadSeleccionada = arguments?.getSerializable("actividad") as Actividad


        // Configuramos el RecyclerView para mostrar las deudas
        saldoAdaptador = SaldoAdaptador(actividadSeleccionada.participantes)
        binding.rvSaldos.layoutManager = LinearLayoutManager(context)
        binding.rvSaldos.adapter = saldoAdaptador

        saldoAdaptador.notifyDataSetChanged()

        return vista
    }
}
