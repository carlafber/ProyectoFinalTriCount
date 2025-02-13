package com.example.proyectofinal.proveedores

import android.util.Log
import com.example.proyectofinal.modelos.Actividad
import com.example.proyectofinal.modelos.Gasto
import com.example.proyectofinal.modelos.Participante

class ActividadProveedor {
    companion object {
        val listaActividades = mutableListOf<Actividad>(
            Actividad(
                "Viaje fin de curso",
                mutableListOf(
                    Participante("Juan", "juan@email.com"),
                    Participante("Ana", "ana@email.com"),
                    Participante("María", "maria@email.com")
                ),
                mutableListOf(
                    Gasto(
                        "Hotel", 300.0, Participante("Juan", "juan@email.com"), mutableListOf(
                            Participante("Juan", "juan@email.com"),
                            Participante("Ana", "ana@email.com"),
                            Participante("María", "maria@email.com")
                        )
                    ),
                    Gasto(
                        "Comida", 90.0, Participante("Ana", "ana@email.com"), mutableListOf(
                            Participante("Juan", "juan@email.com"),
                            Participante("Ana", "ana@email.com"),
                            Participante("María", "maria@email.com")
                        )
                    )
                )
            ),
            Actividad(
                "Comida",
                mutableListOf(
                    Participante("Paco", "paco@email.com"),
                    Participante("Marta", "marta@email.com")
                ),
                mutableListOf(
                    Gasto(
                        "Restaurante", 50.0, Participante("Paco", "paco@email.com"), mutableListOf(
                            Participante("Paco", "paco@email.com"),
                            Participante("Marta", "marta@email.com")
                        )
                    )
                )
            ),
            Actividad(
                "Nochevieja",
                mutableListOf(
                    Participante("Carlos", "carlos@email.com"),
                    Participante("Pedro", "pedro@email.com"),
                    Participante("Elena", "elena@email.com")
                ),
                mutableListOf(
                    Gasto(
                        "Champán", 60.0, Participante("Carlos", "carlos@email.com"), mutableListOf(
                            Participante("Carlos", "carlos@email.com"),
                            Participante("Pedro", "pedro@email.com"),
                            Participante("Elena", "elena@email.com")
                        )
                    ),
                    Gasto(
                        "Aperitivos", 45.0, Participante("Elena", "elena@email.com"), mutableListOf(
                            Participante("Carlos", "carlos@email.com"),
                            Participante("Pedro", "pedro@email.com"),
                            Participante("Elena", "elena@email.com")
                        )
                    )
                )
            )
        )

        val actividades_seleccionadas = mutableListOf<Int>()

        /**
         * Calcula el balance de cada participante en una actividad.
         * Balance positivo significa que le deben dinero.
         * Balance negativo significa que debe dinero.
         */
        fun calcularBalance(actividad: Actividad) {
            val saldo = mutableMapOf<Participante, Double>()

            // Inicializar el saldo de cada participante
            actividad.participantes.forEach { saldo[it] = 0.0 }

            // Calcular pagos y deudas de cada gasto
            actividad.gastos.forEach { gasto ->
                //saldo[gasto.pagador] = saldo[gasto.pagador]!! + gasto.precio  // Lo que pagó el pagador
                val cuota = gasto.precio / gasto.deudores.size

                gasto.deudores.forEach { deudor ->
                    saldo[deudor] = saldo[deudor]!! - cuota  // Lo que debe pagar cada deudor
                }
            }

            // Asignar el balance a cada participante
            actividad.participantes.forEach {
                it.balance = saldo[it] ?: 0.0
                Log.i("Balance", "Participante: ${it.nombre}, Balance: ${it.balance}")
            }
        }


        fun calcularDeudas(actividad: Actividad): List<Triple<Participante, Double, Participante>> {
            val deudas = mutableListOf<Triple<Participante, Double, Participante>>()

            // Lista de personas con balance positivo (acreedores)
            val acreedores: MutableList<Participante> = mutableListOf<Participante>().apply {
                addAll(actividad.participantes.filter { it.balance > 0 }.sortedByDescending { it.balance })
            }

            val deudores: MutableList<Participante> = mutableListOf<Participante>().apply {
                addAll(actividad.participantes.filter { it.balance < 0 }.sortedBy { it.balance })
            }

            // Lista de personas con balance negativo (deudores)
            //val deudores = actividad.participantes.filter { it.balance < 0 }.sortedBy { it.balance }

            for(d in deudores){
                for(g in actividad.gastos){
                    if(d.nombre == g.pagador.nombre){
                        acreedores.add(g.pagador)
                        deudores.remove(g.pagador)
                    }
                }

            }

            Log.i("Deudas", "Acreedores: $acreedores")
            Log.i("Deudas", "Deudores: $deudores")

            // Inicializamos los índices para acreedores y deudores
            var i = 0
            var j = 0

            while (i < acreedores.size && j < deudores.size) {
                val acreedor = acreedores[i]
                val deudor = deudores[j]

                // Si ambos balances son cero, salimos del bucle
                if (acreedor.balance == 0.0 && deudor.balance == 0.0) break

                val deuda = minOf(acreedor.balance, -deudor.balance)

                // Agregar la deuda incluso si el balance se ajusta a 0
                if (deuda > 0) {
                    deudas.add(Triple(deudor, deuda, acreedor))
                }


                // Ajustar balances
                acreedor.balance -= deuda
                deudor.balance += deuda

                // Mover al siguiente acreedor/deudor si su balance ya es 0
                if (acreedor.balance == 0.0) i++
                if (deudor.balance == 0.0) j++
            }

            // Log para ver las deudas calculadas
            Log.i("Deudas", "Deudas calculadas: $deudas")
            return deudas
        }
    }
}
