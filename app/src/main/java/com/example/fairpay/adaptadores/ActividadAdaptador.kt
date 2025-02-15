package com.example.fairpay.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.fairpay.MainActivity
import com.example.fairpay.R
import com.example.fairpay.databinding.ElementoActividadBinding
import com.example.fairpay.modelos.Actividad

class ActividadAdaptador(val actividades: List<Actividad>, val seleccionados: List<Int>, val onClickLargo: (Int) -> Unit, val onclickCorto: (Int) -> Unit) : RecyclerView.Adapter<ActividadAdaptador.GrupoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrupoViewHolder {
        //Este metodo crea un ViewHolder, se crearan como máximo unos 7 u 8 depende de la pantalla y del tamaño del elemento
        //Tiene que obtener un inflador de vistas
        val minflater = LayoutInflater.from(parent.context)

        val miView = minflater.inflate(R.layout.elemento_actividad, parent, false)
        //Retorno el viewHolder y le paso la vista inflada de un elemento
        return GrupoViewHolder(miView)
    }

    override fun getItemCount(): Int =
        //Solamente tiene que devolver el nº de elementos que habría que pintar
        actividades.size


    override fun onBindViewHolder(holder: GrupoViewHolder, position: Int) {
        //Se invoca por cada elemento que se visualiza
        holder.vincular(actividades[position], seleccionados.contains(position))

        holder.vista.setOnClickListener {
            onclickCorto(position)
        }

        holder.vista.setOnLongClickListener {
            onClickLargo(position)
            //Para que se consuma el evento
            false
        }
    }


    class GrupoViewHolder(val vista: View) : ViewHolder(vista) {
        val binding = ElementoActividadBinding.bind(vista)

        fun vincular(actividad: Actividad, seleccionado: Boolean) {
            binding.tvNombreActividad.text = actividad.nombre
            binding.tvParticipantes.text = "Número de participantes: " + actividad.participantes.size

            if (MainActivity.actionMode_activo && seleccionado) {
                print("a")

            } else {
                print("b")
            }
        }
    }

}

