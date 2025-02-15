package com.example.fairpay.adaptadores

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
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
        val seleccionada = seleccionados.contains(position)
        //Se invoca por cada elemento que se visualiza
        holder.vincular(actividades[position], seleccionada)

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

            if(seleccionado){
                Log.i("SEL", "SELECCIONADA")
            }

            if (seleccionado) {
                // Si está seleccionado, cambia el color de fondo a "seleccionado", de lo contrario a "sin_seleccionar"
                binding.card.backgroundTintList = ContextCompat.getColorStateList(vista.context,
                    if (seleccionado) R.color.seleccionado else R.color.sin_selecionar)
            } else {
                // Si el ActionMode no está activo, pone el color predeterminado
                binding.card.backgroundTintList = ContextCompat.getColorStateList(vista.context, R.color.sin_selecionar)
            }
        }
    }

}

