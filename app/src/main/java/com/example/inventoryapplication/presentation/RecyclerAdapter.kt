package com.example.inventoryapplication.presentation

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventoryapplication.R
import com.example.inventoryapplication.domain.Goods
import com.example.inventoryapplication.databinding.GoodsItemBinding
import com.example.inventoryapplication.presentation.tools.GoodsDiffCallback
import com.example.inventoryapplication.presentation.tools.GoodsDiffUtilCallback
import com.example.inventoryapplication.presentation.tools.IGoods

class RecyclerAdapter(private val listener: IGoods): ListAdapter<Goods, GoodsViewHolder>(GoodsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        val view = LayoutInflater.from((parent.context)).inflate(R.layout.goods_item, parent, false)
        return GoodsViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

//    override fun getItemCount(): Int  = goodsList.size
}
