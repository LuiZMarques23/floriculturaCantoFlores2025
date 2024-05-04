package com.marcos.floriculturacantodaflores.activities.SobreApp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.marcos.floriculturacantodaflores.R
import com.marcos.floriculturacantodaflores.databinding.ActivityTermosUsoBinding

class TermosUso : AppCompatActivity() {

    lateinit var binding: ActivityTermosUsoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermosUsoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        confgCliks()
        corStatusBar()

    }

    private fun confgCliks(){
        val toolbarContato = binding.toolbarContato
        toolbarContato.setNavigationOnClickListener {
            finish()
        }

    }

    private fun corStatusBar() {
        window.statusBarColor = Color.parseColor("#3F51B5")
    }
}