package com.example.proyectofinal.proveedores

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
    }
}