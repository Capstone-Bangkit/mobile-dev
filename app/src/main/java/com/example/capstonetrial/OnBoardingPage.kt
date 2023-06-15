package com.example.capstonetrial

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.example.capstonetrial.databinding.ActivityDetailBinding
import com.example.capstonetrial.databinding.ActivityOnBoardingPageBinding

class OnBoardingPage : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        binding.button.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

}