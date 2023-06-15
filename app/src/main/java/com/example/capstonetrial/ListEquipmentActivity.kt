package com.example.capstonetrial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonetrial.adapter.ListAdapter
import com.example.capstonetrial.databinding.ActivityListEquipmentBinding
import com.example.capstonetrial.detail.DetailActivity
import com.example.capstonetrial.response.Equipment

class ListEquipmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListEquipmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListEquipmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "List Equipment"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvListEquipment.setHasFixedSize(true)
        binding.rvListEquipment.layoutManager = LinearLayoutManager(this@ListEquipmentActivity)

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ListViewModel::class.java)
        viewModel.listEquipment.observe(this){equipment ->
            setEquipment(equipment.payload)

            Log.d("test aja", equipment.payload.toString())
        }
        viewModel.isLoading.observe(this){
            showLoading(it)
        }


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }


    fun setEquipment(equipment:List<Equipment>){
        val adapter = ListAdapter(getEquipment(equipment))
        binding.rvListEquipment.adapter = adapter
        Log.e("testing", "tester")
        adapter.setOnItemClickCallback(object:ListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Equipment) {
                val intent = Intent(this@ListEquipmentActivity, DetailActivity::class.java)
                intent.putExtra("id", data.equipment_id)
                startActivity(intent)
            }

        })
    }

    fun getEquipment(user:List<Equipment>):List<Equipment>{
        val array = ArrayList<Equipment>()
        for (getList in user){
            val users = Equipment(getList.equipment_id, getList.name, getList.description, getList.image_url)
            array.add(users)
        }
        return array
    }

    private fun showLoading(state: Boolean){
        if(state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

}