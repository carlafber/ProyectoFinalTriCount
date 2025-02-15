package com.example.fairpay.modelos

import java.io.Serializable

data class Saldo(
    val deudor: Participante,      // El participante que debe dinero
    val acreedor: Participante,    // El participante que debe recibir dinero
    val cantidad: Double           // La cantidad que debe el deudor
) : Serializable