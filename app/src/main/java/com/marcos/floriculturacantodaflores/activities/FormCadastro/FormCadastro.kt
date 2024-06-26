package com.marcos.floriculturacantodaflores.activities.FormCadastro

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.marcos.floriculturacantodaflores.activities.Pagamento.Pagamento
import com.marcos.floriculturacantodaflores.activities.SobreApp.PolicicaPrivacidade
import com.marcos.floriculturacantodaflores.activities.SobreApp.SobreApp
import com.marcos.floriculturacantodaflores.activities.SobreApp.TermosUso
import com.marcos.floriculturacantodaflores.databinding.ActivityFormCadastroBinding

import com.marcos.floriculturacantodaflores.model.DB

class FormCadastro : AppCompatActivity() {

    lateinit var binding: ActivityFormCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        val db = DB()
        corStatusBar()

        binding.btCadastrar.setOnClickListener {


            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Termos uso")
            alertDialog.setMessage("Bem-vindo aos nossos Termos de uso!")
            alertDialog.setPositiveButton("Aceito termos",{ _, _ ->


            val nome = binding.editNome.text.toString()
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(it,"Preencha todos os campos!",Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
            }else{
               FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener { tarefa ->
                   if (tarefa.isSuccessful){
                       db.salvarDadosUsuario(nome)
                       val snackbar = Snackbar.make(it,"Cadastro realizado com sucesso!",Snackbar.LENGTH_SHORT)
                       snackbar.setBackgroundTint(Color.BLUE)
                       snackbar.setTextColor(Color.WHITE)
                       snackbar.show()
                   }
               }.addOnFailureListener { erroCadastro ->

                   val mensagemErro = when(erroCadastro){
                       is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo 6 caracteres"
                       is FirebaseAuthUserCollisionException -> "Esta conta já foi cadastrada"
                       is FirebaseNetworkException -> "Sem conexão com a internet"
                       else -> "Erro ao cadastrar usuário"
                   }
                   val snackbar = Snackbar.make(it,mensagemErro,Snackbar.LENGTH_SHORT)
                   snackbar.setBackgroundTint(Color.RED)
                   snackbar.setTextColor(Color.WHITE)
                   snackbar.show()
               }
            }


            })
            alertDialog.setNegativeButton("Ler termos",{ _, _ ->
                val intent = Intent(this, TermosUso::class.java)
                startActivity(intent)

            })
            alertDialog.show()

        }


    }

    private fun corStatusBar() {
        window.statusBarColor = Color.parseColor("#FFFFFFFF")
    }
}