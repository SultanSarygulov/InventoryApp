package com.example.inventoryapplication.presentation

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventoryapplication.databinding.GoodsItemBinding
import com.example.inventoryapplication.domain.Goods
import com.example.inventoryapplication.presentation.tools.IGoods

class GoodsViewHolder(item: View, private val listener: IGoods): RecyclerView.ViewHolder(item){


    private val binding = GoodsItemBinding.bind(item)
    @SuppressLint("SetTextI18n")
    fun bind(goods: Goods) = with(binding) {
        name.text = goods.name
        cost.text = "$ ${goods.cost}"
        brand.text = goods.brand
        amount.text = "${goods.amount} шт"
        Glide
            .with(root)
            .load(goods.photo)
            .into(image)

        goodsItem.setOnClickListener {
            listener.onItemClick(goods)
        }

        popupMenu.setOnClickListener {
            listener.onPopupMenu(goods)
        }
    }
}