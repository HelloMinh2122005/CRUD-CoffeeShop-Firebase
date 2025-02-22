package com.example.coffeeshop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop.DrinkModel
import com.example.coffeeshop.databinding.EmptyListItemBinding

// bai 16: adapter

class DrinkAdapter(private val list: ArrayList<DrinkModel>) :
    RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder>() {

    // ViewHolder giữ tham chiếu binding thay vì View
    inner class DrinkViewHolder(val binding: EmptyListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        // Chỉ inflate layout 1 lần bằng View Binding
        val binding = EmptyListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DrinkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        holder.binding.cardTitle.text = list[position].drinkName
        holder.binding.cardDescription.text = list[position].drinkType
    }

    override fun getItemCount(): Int = list.size
}
