package com.example.coffeeshop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop.DrinkModel
import com.example.coffeeshop.databinding.EmptyListItemBinding

// bai 16: adapter

class DrinkAdapter(private val list: ArrayList<DrinkModel>) : RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {
        mListener = clickListener
    }

    // ViewHolder giữ tham chiếu binding thay vì View
    inner class DrinkViewHolder(val binding: EmptyListItemBinding, clickListener: onItemClickListener) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        // Chỉ inflate layout 1 lần bằng View Binding
        val binding = EmptyListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DrinkViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        holder.binding.cardTitle.text = list[position].drinkName
        holder.binding.cardDescription.text = list[position].drinkType
    }

    override fun getItemCount(): Int = list.size
}
