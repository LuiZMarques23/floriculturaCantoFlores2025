package com.marcos.floriculturacantodaflores.activities.DetalhesProduto

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.marcos.floriculturacantodaflores.activities.Pagamento.Pagamento
import com.marcos.floriculturacantodaflores.databinding.ActivityDetalhesProdutoBinding


class DetalhesProduto : AppCompatActivity() {

    lateinit var binding: ActivityDetalhesProdutoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        val foto = intent.extras?.getString("foto")
        val nome = intent.extras?.getString("nome")
        val preco = intent.extras?.getString("preco")

        Glide.with(this).load(foto).into(binding.dtFotoProduto)
        binding.dtNomeProduto.text = nome
        binding.dtPrecoProduto.text = "R$ ${preco}"

        binding.btFinalizarPedido.setOnClickListener {

            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Aviso")
            alertDialog.setMessage("Todas compras feita pelo app tem retirar produto na loja, apos finalizar pagamento acompanhar status entrega comparecer a loja com comprovate pagamento!")
            alertDialog.setPositiveButton("Tudo bem",{ _, _ ->

                val intent = Intent(this,Pagamento::class.java)
                intent.putExtra("nome",nome)
                intent.putExtra("preco",preco)
                startActivity(intent)

            })
            alertDialog.setNegativeButton("Não",{ _, _ ->

            })
            alertDialog.show()


        }

       corStatusBar()
    }
    private fun corStatusBar() {
        window.statusBarColor = Color.parseColor("#3F51B5")
    }
}