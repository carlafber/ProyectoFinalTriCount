package com.example.proyectofinal.modelos

import android.util.Log
import java.io.Serializable

data class Gasto(
    var nombre:String,
    var precio:Double,
    var pagador:Participante,
    var deudores:MutableList<Participante>
):Serializable{
    // Este método distribuye el gasto entre los deudores y actualiza los balances.
    fun distribuirGasto() {
        // Calcular cuánto le toca pagar a cada deudor
        val cantidadPorPersona = precio / deudores.size

        // El pagador no tiene deuda, pero los deudores deben su parte.
        for (deudor in deudores) {
            // Restar la cuota de cada deudor
            deudor.balance -= cantidadPorPersona

            // Sumar la cuota al pagador (es quien cubrió el gasto por los demás)
            pagador.balance += cantidadPorPersona

            // Logs para ver cómo va quedando el saldo de cada uno
            Log.i("deudor", "${deudor.nombre} -> ${deudor.balance}")
        }

        // Log para ver el saldo final del pagador
        Log.i("pagador", "${pagador.nombre} -> ${pagador.balance}")
    }

}