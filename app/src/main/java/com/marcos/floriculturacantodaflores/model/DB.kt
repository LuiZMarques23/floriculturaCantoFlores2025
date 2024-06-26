package com.marcos.floriculturacantodaflores.model

import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.marcos.floriculturacantodaflores.adapter.AdapterPedido
import com.marcos.floriculturacantodaflores.adapter.AdapterProduto

class DB {

    fun salvarDadosUsuario(nome: String){

        val usuarioID = FirebaseAuth.getInstance().currentUser!!.uid
        val db = FirebaseFirestore.getInstance()

        val usuarios = hashMapOf(
            "nome" to nome
        )

        val documentReference: DocumentReference = db.collection("Usuarios").document(usuarioID)
        documentReference.set(usuarios).addOnSuccessListener {
            Log.d("DB","Sucesso ao salvar os dados!")
        }.addOnFailureListener { erro ->
            Log.d("DB_ERROR","Erro ao salvar os dados! ${erro.printStackTrace()}")
        }
    }

    fun recuperarDadosUsuarioPerfil(nomeUsuario: TextView, emailUsuario: TextView){

        val usuarioID = FirebaseAuth.getInstance().currentUser!!.uid
        val email = FirebaseAuth.getInstance().currentUser!!.email
        val db = FirebaseFirestore.getInstance()

        val documentReference: DocumentReference = db.collection("Usuarios").document(usuarioID)
        documentReference.addSnapshotListener { documento, error ->
            if (documento != null){
                nomeUsuario.text = documento.getString("nome")
                emailUsuario.text = email
            }
        }
    }

    fun obterListaDeProdutos(lista_produtos: MutableList<Produto>, adapter_produto: AdapterProduto){

        val db = FirebaseFirestore.getInstance()

        db.collection("Produtos").get()
            .addOnCompleteListener { tarefa ->
                if (tarefa.isSuccessful){
                    for (documento in tarefa.result!!){
                        val produtos = documento.toObject(Produto::class.java)
                        lista_produtos.add(produtos)
                        adapter_produto.notifyDataSetChanged()
                    }
                }
            }
    }

    fun salvarDadosPedidosUsuario(
        endereco: String,
        celular: String,
        produto: String,
        preco: String,
        status_pagamento: String,
        status_entrega: String,
        pedidoID: String,
        usuarioID: String
    ){

        var db = FirebaseFirestore.getInstance()
//        var usuarioID = FirebaseAuth.getInstance().currentUser!!.uid
//        var pedidoID = UUID.randomUUID().toString()

        val pedidos = hashMapOf(
            "endereco" to endereco,
            "celular" to celular,
            "produto" to produto,
            "preco" to preco,
            "status_pagamento" to status_pagamento,
            "status_entrega" to status_entrega,
            "usuarioID" to usuarioID,
            "pedidoID" to pedidoID
        )

        val documentReference = db.collection("Usuario_Pedidos").document(usuarioID)
            .collection("Pedidos").document(pedidoID)
        documentReference.set(pedidos).addOnSuccessListener {
            Log.d("db_pedido","Sucesso ao salvar os pedidos!")
        }
    }

    fun salvarDadosPedidosAdmin(
        endereco: String,
        celular: String,
        produto: String,
        preco: String,
        status_pagamento: String,
        status_entrega: String,
        pedidoID: String,
        usuarioID: String
    ){

        var db = FirebaseFirestore.getInstance()


        val pedidos = hashMapOf(
            "endereco" to endereco,
            "celular" to celular,
            "produto" to produto,
            "preco" to preco,
            "status_pagamento" to status_pagamento,
            "status_entrega" to status_entrega,
            "pedidoID" to pedidoID,
            "usuarioID" to usuarioID
        )

        val documentReference = db.collection("Pedidos_Admin").document(pedidoID)
        documentReference.set(pedidos).addOnSuccessListener {
            Log.d("db_pedido","Sucesso ao salvar os pedidos!")
        }
    }

    fun obterListaDePedidos(lista_pedidos: MutableList<Pedido>, adapter_pedidos: AdapterPedido){

        var db = FirebaseFirestore.getInstance()
        var usuarioID = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("Usuario_Pedidos").document(usuarioID).collection("Pedidos")
            .get().addOnCompleteListener { tarefa ->
                if (tarefa.isSuccessful){
                    for (documento in tarefa.result!!){
                        val pedidos = documento.toObject(Pedido::class.java)
                        lista_pedidos.add(pedidos)
                        adapter_pedidos.notifyDataSetChanged()
                    }
                }
            }
    }
}