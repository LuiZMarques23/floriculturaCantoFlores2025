package com.marcos.floriculturacantodaflores.activities.SobreApp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.marcos.floriculturacantodaflores.R
import com.marcos.floriculturacantodaflores.activities.Contato.Contato
import com.marcos.floriculturacantodaflores.activities.Pagamento.Pagamento
import com.marcos.floriculturacantodaflores.databinding.ActivitySobreAppBinding

class SobreApp : AppCompatActivity() {

    lateinit var binding: ActivitySobreAppBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySobreAppBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar!!.hide()
        confiCks()
        corStatusBar()

        binding.btnVersaoApp.setOnClickListener {

            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Sobre o Aplicativo")
            alertDialog.setMessage("Versão 1.0,esta é versão mais recente do aplicativo.")
            alertDialog.setPositiveButton("OK ",{ _, _ ->



            })
            alertDialog.setNegativeButton("",{ _, _ ->

            })
            alertDialog.show()


        }

    }

    private fun confiCks(){

        val toolbarContato = binding.toolbarContato
        toolbarContato.setNavigationOnClickListener {
            finish()
        }

        binding.btnPolicicaPrivacidade.setOnClickListener {
            val intent = Intent(this, PolicicaPrivacidade::class.java)
            startActivity(intent)
        }

        binding.btnTermosUso.setOnClickListener {
            val intent = Intent(this, TermosUso::class.java)
            startActivity(intent)
        }

    }


    private fun corStatusBar() {
        window.statusBarColor = Color.parseColor("#3F51B5")
    }
}