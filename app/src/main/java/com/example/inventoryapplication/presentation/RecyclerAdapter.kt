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
import java.util.*

class RecyclerAdapter(private val listener: IGoods): ListAdapter<Goods, GoodsViewHolder>(GoodsDiffCallback()) {

    private var unfilteredList = listOf<Goods>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        val view = LayoutInflater.from((parent.context)).inflate(R.layout.goods_item, parent, false)
        return GoodsViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun modifyList(list : List<Goods>) {
        unfilteredList = list
        submitList(list)
    }

    fun filter(query: CharSequence?) {
        val list = mutableListOf<Goods>()

        // perform the data filtering
        if(!query.isNullOrEmpty()) {
            list.addAll(unfilteredList.filter {
                it.name.lowercase(Locale.getDefault()).contains(query.toString().lowercase(Locale.getDefault()))})
        } else {
            list.addAll(unfilteredList)
        }

        submitList(list)
    }
}
