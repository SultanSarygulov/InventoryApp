package com.example.inventoryapplication.presentation

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventoryapplication.R
import com.example.inventoryapplication.domain.Goods
import com.example.inventoryapplication.databinding.GoodsItemBinding
import com.example.inventoryapplication.presentation.tools.GoodsDiffUtilCallback
import com.example.inventoryapplication.presentation.tools.IGoods

class RecyclerAdapter(private val listener: IGoods): RecyclerView.Adapter<RecyclerAdapter.GoodsViewHolder>() {

    var goodsList = mutableListOf<Goods>()

    fun setList(newList: List<Goods>) {
        Log.i("TAG", "setList")
        val diffCallback = GoodsDiffUtilCallback(goodsList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        goodsList.clear()
        goodsList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class GoodsViewHolder(item: View): RecyclerView.ViewHolder(item){
            val binding = GoodsItemBinding.bind(item)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        val view = LayoutInflater.from((parent.context)).inflate(R.layout.goods_item, parent, false)
        return GoodsViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        holder.bind(goodsList[position])
    }

    override fun getItemCount(): Int  = goodsList.size
}
