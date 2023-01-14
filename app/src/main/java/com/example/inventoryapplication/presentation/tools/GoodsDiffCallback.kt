package com.example.inventoryapplication.presentation.tools

import androidx.recyclerview.widget.DiffUtil
import com.example.inventoryapplication.data.Goods

class GoodsDiffCallback:DiffUtil.ItemCallback<Goods>() {
    override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean {
        return oldItem == newItem
    }
}