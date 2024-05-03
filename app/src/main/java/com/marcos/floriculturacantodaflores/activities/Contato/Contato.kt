package com.marcos.floriculturacantodaflores.activities.Contato

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marcos.floriculturacantodaflores.databinding.ActivityContatoBinding

class Contato : AppCompatActivity() {

    lateinit var binding: ActivityContatoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContatoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        corStatusBar()

        val toolbarContato = binding.toolbarContato
        toolbarContato.setNavigationOnClickListener {
            finish()
        }

        binding.btWhatsApp.setOnClickListener {
            abrirWhatsApp()
        }



    }

    private fun abrirWhatsApp(){

        val numeroTelefone = "5531997453602"
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$numeroTelefone")
        val intent = Intent(Intent.ACTION_VIEW,uri)
        startActivity(intent)

        if (numeroTelefone.equals(numeroTelefone)){
            startActivity(intent)
        }else{
            val playStoreIntent = Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp"))
            startActivity(playStoreIntent)
        }

    }
    private fun corStatusBar() {
        window.statusBarColor = Color.parseColor("#3F51B5")
    }
}