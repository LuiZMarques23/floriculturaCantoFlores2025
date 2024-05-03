package com.marcos.floriculturacantodaflores.model

data class Pedido(
    val endereco: String? = null,
    val celular: String? = null,
    val produto: String? = null,
    val preco: String? = null,
    val status_pagamento: String? = null,
    val status_entrega: String? = null,
    val usuarioID: String? = null,
    val pedidoID: String? = null
)
