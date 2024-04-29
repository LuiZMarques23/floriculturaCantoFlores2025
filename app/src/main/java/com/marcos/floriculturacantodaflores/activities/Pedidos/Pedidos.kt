package com.marcos.floriculturacantodaflores.activities.Pedidos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcos.floriculturacantodaflores.adapter.AdapterPedido
import com.marcos.floriculturacantodaflores.databinding.ActivityPedidosBinding
import com.marcos.floriculturacantodaflores.model.DB
import com.marcos.floriculturacantodaflores.model.Pedido

class Pedidos : AppCompatActivity() {

    lateinit var binding: ActivityPedidosBinding
    lateinit var adapterPedidos: AdapterPedido
    var lista_pedidos: MutableList<Pedido> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPedidosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler_pedidos = binding.recyclerPedidos
        recycler_pedidos.layoutManager = LinearLayoutManager(this)
        recycler_pedidos.setHasFixedSize(true)
        adapterPedidos = AdapterPedido(this,lista_pedidos)
        recycler_pedidos.adapter = adapterPedidos

        val db = DB()
        db.obterListaDePedidos(lista_pedidos,adapterPedidos)
    }
}