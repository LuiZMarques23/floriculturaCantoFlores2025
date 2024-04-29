package com.marcos.floriculturacantodaflores.activities.FormLogin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.marcos.floriculturacantodaflores.activities.FormCadastro.FormCadastro
import com.marcos.floriculturacantodaflores.activities.RecuperarSenha.RecuperarSenha
import com.marcos.floriculturacantodaflores.activities.TelaPrincipalProdutos.TelaPrincipalProdutos
import com.marcos.floriculturacantodaflores.databinding.ActivityFormLoginBinding

import com.marcos.floriculturacantodaflores.dialog.DialogCarregando

class FormLogin : AppCompatActivity() {

    lateinit var binding: ActivityFormLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        val dialogCarregando = DialogCarregando(this)

        binding.btEntrar.setOnClickListener {view ->

            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(view,"Preencha todos os campos",Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
            }else{
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha).addOnCompleteListener { tarefa ->
                    if (tarefa.isSuccessful){
                        dialogCarregando.iniciarCarregamentoAlertDialog()
                        Handler(Looper.getMainLooper()).postDelayed({
                            irParaTelaDeProdutos()
                            dialogCarregando.liberarAlertDialog()
                        },3000)

                    }
                }.addOnFailureListener {
                    val snackbar = Snackbar.make(view,"Erro ao fazer o login!",Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.setTextColor(Color.WHITE)
                    snackbar.show()
                }
            }
        }

        conficks()

    }

    private fun conficks(){
        binding.btnRecuraSenha.setOnClickListener {
            val intent = Intent(this, RecuperarSenha:: class.java)
            startActivity(intent)
        }

        binding.txtTelaCadastro.setOnClickListener {
            val intent = Intent(this,FormCadastro::class.java)
            startActivity(intent)
        }

    }

    private fun irParaTelaDeProdutos(){
        val intent = Intent(this,TelaPrincipalProdutos::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()

        val usuarioAtual = FirebaseAuth.getInstance().currentUser

        if (usuarioAtual != null){
            irParaTelaDeProdutos()
        }
    }
}