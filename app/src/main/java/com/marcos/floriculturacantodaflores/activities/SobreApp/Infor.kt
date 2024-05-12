package com.marcos.floriculturacantodaflores.activities.SobreApp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.marcos.floriculturacantodaflores.R
import com.marcos.floriculturacantodaflores.databinding.ActivityInforBinding

class Infor : AppCompatActivity() {

    lateinit var binding: ActivityInforBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInforBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        confgCliks()



    }

    private fun confgCliks() {
        val toolbarContato = binding.toolbarContato
        toolbarContato.setNavigationOnClickListener {
            finish()
        }

    }

}

