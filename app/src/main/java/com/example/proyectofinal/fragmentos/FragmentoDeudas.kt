package com.example.proyectofinal.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinal.R
import com.example.proyectofinal.adaptadores.DeudaAdaptador
import com.example.proyectofinal.databinding.FragmentoDeudasBinding
import com.example.proyectofinal.modelos.Actividad

class FragmentoDeudas : Fragment() {

    private lateinit var actividadSeleccionada: Actividad
    private lateinit var deudaAdaptador: DeudaAdaptador

    companion object {
        fun nuevaInstancia(actividad: Actividad): FragmentoDeudas {
            val fragment = FragmentoDeudas()
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
        val vista = inflater.inflate(R.layout.fragmento_deudas, container, false)

        val binding = FragmentoDeudasBinding.bind(vista)
        // Recuperamos la actividad pasada como argumento
        actividadSeleccionada = arguments?.getSerializable("actividad") as Actividad


        // Configuramos el RecyclerView para mostrar las deudas
        deudaAdaptador = DeudaAdaptador(actividadSeleccionada.participantes)
        binding.rvDeudas.layoutManager = LinearLayoutManager(context)
        binding.rvDeudas.adapter = deudaAdaptador

        deudaAdaptador.notifyDataSetChanged()

        return vista
    }
}
