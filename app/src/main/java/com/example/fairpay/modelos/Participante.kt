package com.example.fairpay.modelos

import java.io.Serializable

data class Participante(
    val nombre: String,
    val email: String,
    var balance: Double = 0.0
):Serializable