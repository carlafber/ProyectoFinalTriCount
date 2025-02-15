package com.example.fairpay.modelos

import android.util.Log
import java.io.Serializable

data class Gasto(
    var nombre:String,
    var precio:Double,
    var pagador:Participante,
    var deudores:MutableList<Participante>
):Serializable{
    // Este método distribuye el gasto entre los deudores y actualiza los balances.
    fun distribuirGasto(actividad: Actividad) {
        // Calcular cuánto le toca pagar a cada deudor
        val cantidadPorPersona = precio / actividad.participantes.size

        // El pagador no tiene deuda, pero los deudores deben su parte.
        val pagador = actividad.participantes.first { it.nombre == pagador.nombre }
        val deudores = actividad.participantes.filter { it.nombre != pagador.nombre }

        // Recorrer los deudores y actualizar sus balances
        for (deudor in deudores) {
            // Restar la cuota de cada deudor
            deudor.balance -= cantidadPorPersona

            // Sumar la cuota al pagador (es quien cubrió el gasto por los demás)
            pagador.balance += cantidadPorPersona

            // Logs para ver cómo va quedando el saldo de cada uno
            Log.i("deudor", "${deudor.nombre} ahora debe -> ${deudor.balance}")
        }

        // Log para ver el saldo final del pagador
        Log.i("pagador", "${pagador.nombre} ha pagado -> ${pagador.balance}")
        // Ahora, después de distribuir el gasto, calculamos las deudas
        calcularDeudas(actividad)
    }

    private fun calcularDeudas(actividad: Actividad) {
        // Crear una lista de deudas basadas en los balances actuales de los participantes
        val saldos = mutableListOf<Saldo>()

        // Calcular las deudas: un deudor debe la diferencia entre lo que ha pagado y lo que le tocaba pagar
        for (participante in actividad.participantes) {
            // Aquí asumimos que el saldo negativo representa deuda
            if (participante.balance < 0) {
                val saldo = Saldo(
                    deudor = participante,
                    acreedor = actividad.participantes.first { it.balance > 0 }, // Puedes modificar este criterio según la lógica que necesites
                    cantidad = Math.abs(participante.balance) // La deuda es el valor absoluto del balance
                )
                saldos.add(saldo)
                //Log.i("Deuda", "${participante.nombre} debe ${Math.abs(participante.balance)}")
                Log.i("Deuda1", "${saldo.deudor.nombre} debe ${Math.abs(saldo.cantidad)} a ${saldo.acreedor.nombre}")
            }
        }

        // Log para mostrar las deudas calculadas
        Log.i("Deudas", "Deudas calculadas: $saldos")
    }
}