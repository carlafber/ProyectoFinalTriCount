package com.example.proyectofinal
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.adaptadores.ParticipanteAdaptador
import com.example.proyectofinal.modelos.Participante

class DialogoEditarActividad(
    private var nombreActividad: String,
    private var participantes: MutableList<Participante>,
    private val onGuardarCambios: (String, MutableList<Participante>) -> Unit
) : DialogFragment() {

    private lateinit var txtNombreActividad: EditText
    private lateinit var txtNuevoParticipante: EditText
    private lateinit var rvParticipantes: RecyclerView
    private lateinit var adapter: ParticipanteAdaptador


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialogo_editar_actividad, null)

        txtNombreActividad = view.findViewById(R.id.txtNombreActividad)
        txtNuevoParticipante = view.findViewById(R.id.txtNuevoParticipante)
        rvParticipantes = view.findViewById(R.id.rvParticipantes)
        val btNuevoParticipante = view.findViewById<Button>(R.id.btNuevoParticipanteEditar)
        val btGuardar = view.findViewById<Button>(R.id.btGuardar)
        val btCancelar = view.findViewById<Button>(R.id.btCancelar)

        txtNombreActividad.setText(nombreActividad)

        rvParticipantes.layoutManager = LinearLayoutManager(context)
        adapter = ParticipanteAdaptador(participantes) { participante ->
            participantes.remove(participante)
            rvParticipantes.adapter?.notifyDataSetChanged()
        }
        rvParticipantes.adapter = adapter

        btNuevoParticipante.setOnClickListener {
            val nombreParticipante = txtNuevoParticipante.text.toString().trim()
            if (nombreParticipante.isNotEmpty()) {
                val nuevoParticipante = Participante(nombreParticipante, "${nombreParticipante.lowercase()}@email.com")
                participantes.add(nuevoParticipante)
                adapter.notifyDataSetChanged()
                txtNuevoParticipante.text.clear()
            }
        }

        btGuardar.setOnClickListener {
            onGuardarCambios(txtNombreActividad.text.toString(), participantes)
            dismiss()
        }

        btCancelar.setOnClickListener {
            dismiss()
        }

        builder.setView(view)
        return builder.create()
    }
}
