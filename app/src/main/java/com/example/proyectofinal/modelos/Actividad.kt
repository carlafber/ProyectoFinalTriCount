package com.example.proyectofinal.modelos

import java.io.Serializable

data class Actividad(
    var nombre:String,
    var participantes:MutableList<Participante> = mutableListOf(),
    var gastos:MutableList<Gasto> = mutableListOf(),
    var deudas: List<Triple<Participante, Double, Participante>> = listOf()
): Serializable
