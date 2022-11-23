package com.example.inventoryapplication.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.databinding.GoodsItemBinding

class RecyclerAdapter(private val listener: IGoods): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    lateinit var binding: GoodsItemBinding
    
    var goodsList = mutableListOf<Goods>()
        set(value){
            val callback = GoodsDiffUtilCallback(goodsList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            goodsList.clear()
            goodsList.addAll(value)
            diffResult.dispatchUpdatesTo(this)
        }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = GoodsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding.root)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentGoods = goodsList[position]

        binding.apply {
            name.text = currentGoods.name
            cost.text = "$ ${currentGoods.cost}"
            brand.text = currentGoods.brand
            amount.text = "${currentGoods.amount} шт"
            Glide
                .with(root)
                .load(currentGoods.photo)
                .into(image)

            goodsItem.setOnClickListener {
                listener.onItemClick(currentGoods)
            }

            popupMenu.setOnClickListener {
                listener.onPopupMenu(currentGoods)
            }
        }
    }

    override fun getItemCount(): Int  = goodsList.size
}