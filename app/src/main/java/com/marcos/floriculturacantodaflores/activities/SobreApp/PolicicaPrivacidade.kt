package com.marcos.floriculturacantodaflores.activities.SobreApp

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.marcos.floriculturacantodaflores.R
import com.marcos.floriculturacantodaflores.databinding.ActivityPolicicaPrivacidadeBinding

class PolicicaPrivacidade : AppCompatActivity() {

    lateinit var binding: ActivityPolicicaPrivacidadeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPolicicaPrivacidadeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        corStatusBar()
    }

    private fun corStatusBar() {
        window.statusBarColor = Color.parseColor("#FFFFFFFF")
    }
}