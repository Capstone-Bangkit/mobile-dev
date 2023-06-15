package com.example.capstonetrial.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.capstonetrial.ListViewModel
import com.example.capstonetrial.databinding.ActivityDetailBinding
import com.example.capstonetrial.response.Equipment

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title="Detail Equipment"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        val id = extras?.getInt("id")


        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailViewModel::class.java)
        if (id != null) {
            viewModel.getEquipmentDetail(id)
        }

        viewModel.listEquipment.observe(this){equipment ->
            setEquipmentDetail(equipment.payload)
            Log.d("test aja", equipment.payload.toString())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
    fun setEquipmentDetail(data: List<Equipment>){
        binding.tvDetailname.text=data[0].name?.replace("_", " ")?.uppercase()
        Glide.with(this).load(data[0].image_url)
            .into(binding.image)
        binding.tvDeskripsi.text=data[0].description

    }
}