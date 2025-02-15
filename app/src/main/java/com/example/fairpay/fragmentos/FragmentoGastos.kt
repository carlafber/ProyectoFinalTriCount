package com.example.fairpay.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fairpay.R
import com.example.fairpay.adaptadores.GastoAdaptador
import com.example.fairpay.databinding.FragmentoGastosBinding
import com.example.fairpay.modelos.Actividad
import com.example.fairpay.modelos.Gasto

class FragmentoGastos : Fragment() {

    private lateinit var binding: FragmentoGastosBinding
    private lateinit var actividadSeleccionada: Actividad
    private lateinit var gastoAdaptador: GastoAdaptador

    companion object {
        fun nuevaInstancia(actividad: Actividad): FragmentoGastos {
            return FragmentoGastos().apply {
                arguments = Bundle().apply {
                    putSerializable("actividad", actividad)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentoGastosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recuperamos la actividad pasada como argumento
        actividadSeleccionada = arguments?.getSerializable("actividad") as? Actividad ?: return

        // Configurar RecyclerView
        gastoAdaptador = GastoAdaptador(actividadSeleccionada.gastos)
        binding.rvGastos.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGastos.adapter = gastoAdaptador

        // Configurar FAB
        binding.fabNuevoGasto.setOnClickListener {
            mostrarDialogoNuevoGasto()
        }
    }

    private fun mostrarDialogoNuevoGasto() {
        val contexto = requireContext()

        // Inflar el layout del diálogo
        val dialogView = LayoutInflater.from(contexto).inflate(R.layout.dialogo_crear_gasto, null)

        // Referencias a los elementos del diálogo
        val nombre = dialogView.findViewById<EditText>(R.id.txtNombreNuevoGasto)
        val cantidad = dialogView.findViewById<EditText>(R.id.txtCantidad)
        val spinnerPagador = dialogView.findViewById<Spinner>(R.id.spPagador)
        val btGuardar = dialogView.findViewById<Button>(R.id.btGuardarGasto)
        val btCancelar = dialogView.findViewById<Button>(R.id.btCancelarGasto)

        // Cargar los participantes en el Spinner
        val participantes = actividadSeleccionada.participantes
        val adapter = ArrayAdapter(contexto, android.R.layout.simple_spinner_dropdown_item, participantes.map { it.nombre })
        spinnerPagador.adapter = adapter

        // Construir el diálogo
        val dialogo = AlertDialog.Builder(contexto)
            .setView(dialogView)
            .create()

        btGuardar.setOnClickListener{
            val titulo = nombre.text.toString().trim()
            val cantidad = cantidad.text.toString().toDoubleOrNull()
            val pagadorIndex = spinnerPagador.selectedItemPosition

            if (titulo.isNotEmpty() && cantidad != null && pagadorIndex >= 0) {
                val pagador = participantes[pagadorIndex - 1]
                val nuevoGasto = Gasto(titulo, cantidad, pagador, actividadSeleccionada.participantes)

                // Añadir el gasto a la actividad
                actividadSeleccionada.gastos.add(nuevoGasto)

                // Distribuir el gasto entre los participantes
                nuevoGasto.distribuirGasto(actividadSeleccionada)

                // Notificar cambios en la lista de gastos
                gastoAdaptador.notifyDataSetChanged()

                spinnerPagador.clearFocus()

                //Cerrar el diálogo cuando se presiona en botón
                dialogo.dismiss()
            } else {
                Toast.makeText(contexto, "Completa todos los campos", Toast.LENGTH_LONG).show()
            }
        }

        btCancelar.setOnClickListener {
            dialogo.dismiss()
        }
        //mostrar el diálogo
        dialogo.show()
    }
}