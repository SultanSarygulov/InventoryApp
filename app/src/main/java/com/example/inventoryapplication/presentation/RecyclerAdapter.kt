package com.example.inventoryapplication.presentation

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.databinding.GoodsItemBinding

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var goodsList = emptyList<Goods>()

    inner class ViewHolder(val binding: GoodsItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.goods_item, parent, false)
        val binding = GoodsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentGoods = goodsList[position]
        with(holder.binding){
            name.text = currentGoods.name
            cost.text = "$ ${currentGoods.cost}"
            brand.text = currentGoods.brand
            amount.text = "${currentGoods.amount} шт"
            image.load(currentGoods.photo)
        }
    }

    fun setData(goods: List<Goods>){
        this.goodsList = goods
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = goodsList.size
}