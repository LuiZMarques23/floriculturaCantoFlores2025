package com.marcos.floriculturacantodaflores.activities.RecuperarSenha

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.marcos.floriculturacantodaflores.R
import com.marcos.floriculturacantodaflores.databinding.ActivityRecuperarSenhaBinding

class RecuperarSenha : AppCompatActivity() {

    lateinit var binding: ActivityRecuperarSenhaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecuperarSenhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        confclique()
        corStatusBar()
        validaDados()

    }

    private fun confclique(){
        binding.btnREcuVolta.setOnClickListener {
            finish()
        }
    }


    fun validaDados() {

        binding.btnEnvia.setOnClickListener {

            ocultaTeclado()

            val email: String = binding.edtRecupera.getText().toString().trim()
            if (!email.isEmpty()) {
                val snackbar = Snackbar.make(it, "Recuperação foi realizado com sucesso ;)", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.GREEN)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()

                binding.progressBar.visibility = View.VISIBLE
                recuperaConta(email)

            } else {
                val snackbar = Snackbar.make(it, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
            }

        }


    }

    private fun corStatusBar() {
        window.statusBarColor = Color.parseColor("#FFFFFF")
    }

    private fun recuperaConta(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(
            email
        ).addOnCompleteListener { task ->
            if (task.isSuccessful()) {
                Toast.makeText(
                    this,
                    "Acabamos de enviar um link para seu e-mail informado.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Erro.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    // Ocultar teclado do dispositivo
    private fun ocultaTeclado() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            binding.edtRecupera.getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}