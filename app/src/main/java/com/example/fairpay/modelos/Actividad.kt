package com.example.fairpay.modelos

import java.io.Serializable

data class Actividad(
    var nombre:String,
    var participantes:MutableList<Participante> = mutableListOf(),
    var gastos:MutableList<Gasto> = mutableListOf(),
    var saldos: MutableList<Saldo> = mutableListOf()
): Serializable
