package com.example.capstonetrial.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstonetrial.R
import com.example.capstonetrial.databinding.ItemListEquipmentBinding
import com.example.capstonetrial.response.Equipment

class ListAdapter(private val listEquipment: List<Equipment> ) :  RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(var binding: ItemListEquipmentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListEquipmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (equipment_id, name, _, image_url) = listEquipment[position]
        holder.binding.tvEquipmentName.text = name?.replace("_", " ")?.uppercase()
        Glide.with(holder.itemView)
            .load(image_url)
            .into(holder.binding.ivImgEquipment)

        holder.binding.cvEquipment.setOnClickListener{
            onItemClickCallback.onItemClicked(listEquipment[position])
        }
    }

    override fun getItemCount(): Int {
        return listEquipment.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Equipment)
    }
}