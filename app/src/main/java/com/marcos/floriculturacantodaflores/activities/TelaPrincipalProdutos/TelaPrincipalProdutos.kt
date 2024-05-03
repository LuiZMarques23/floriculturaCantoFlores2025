package com.marcos.floriculturacantodaflores.activities.TelaPrincipalProdutos

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager

import com.google.firebase.auth.FirebaseAuth
import com.marcos.floriculturacantodaflores.R
import com.marcos.floriculturacantodaflores.activities.Contato.Contato

import com.marcos.floriculturacantodaflores.activities.FormLogin.FormLogin
import com.marcos.floriculturacantodaflores.activities.Pedidos.Pedidos
import com.marcos.floriculturacantodaflores.activities.SobreApp.SobreApp
import com.marcos.floriculturacantodaflores.adapter.AdapterProduto
import com.marcos.floriculturacantodaflores.databinding.ActivityContatoBinding
import com.marcos.floriculturacantodaflores.databinding.ActivityTelaPrincipalProdutosBinding
import com.marcos.floriculturacantodaflores.dialog.DialogPerfilUsuario
import com.marcos.floriculturacantodaflores.model.DB
import com.marcos.floriculturacantodaflores.model.Produto

class TelaPrincipalProdutos : AppCompatActivity() {

    lateinit var binding: ActivityTelaPrincipalProdutosBinding
    lateinit var adapterProduto: AdapterProduto
    var lista_produtos: MutableList<Produto> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPrincipalProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler_produtos = binding.recyclerProdutos
        recycler_produtos.layoutManager = GridLayoutManager(this,2)
        recycler_produtos.setHasFixedSize(true)
        adapterProduto = AdapterProduto(this,lista_produtos)
        recycler_produtos.adapter = adapterProduto

        val db = DB()
        db.obterListaDeProdutos(lista_produtos,adapterProduto)
        corStatusBar()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.perfil -> iniciarDialogPerfilUsuario()
            R.id.pedidos -> iniciarTelaDePedidos()
            R.id.deslogar -> deslogarUsuario()
            R.id.ajuda -> iniciarContatoUsuario()
            R.id.sobreApp -> iniciarSobreApp()

        }

        return super.onOptionsItemSelected(item)
    }
    private fun iniciarContatoUsuario(){
        val intent = Intent(this, Contato::class.java)
        startActivity(intent)

    }

    private fun iniciarDialogPerfilUsuario(){
        val dialogPerfilUsuario = DialogPerfilUsuario(this)
        dialogPerfilUsuario.iniciarPerfilUsuario()
        dialogPerfilUsuario.recuperarDadosUsuarioBanco()
    }

    private fun iniciarTelaDePedidos(){
     val intent = Intent(this,Pedidos::class.java)
     startActivity(intent)
    }

    private fun deslogarUsuario(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this,FormLogin::class.java)
        startActivity(intent)
        finish()
    }

    private fun iniciarSobreApp(){
        val intent = Intent(this, SobreApp::class.java)
        startActivity(intent)

    }

    private fun corStatusBar() {
        window.statusBarColor = Color.parseColor("#3F51B5")
    }


}