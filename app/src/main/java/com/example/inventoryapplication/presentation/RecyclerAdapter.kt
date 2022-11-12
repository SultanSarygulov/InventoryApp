package com.example.inventoryapplication.presentation

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.inventoryapplication.R
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.databinding.GoodsItemBinding

class RecyclerAdapter(private val viewModelStoreOwner: ViewModelStoreOwner): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var goodsList = emptyList<Goods>()

    inner class ViewHolder(val binding: GoodsItemBinding, val mGoodsViewModel: GoodsViewModel): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.goods_item, parent, false)
        val binding = GoodsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val mGoodsViewModel = ViewModelProvider(viewModelStoreOwner).get(GoodsViewModel::class.java)

        return ViewHolder(binding, mGoodsViewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentGoods = goodsList[position]
        with(holder.binding) {
            name.text = currentGoods.name
            cost.text = "$ ${currentGoods.cost}"
            brand.text = currentGoods.brand
            amount.text = "${currentGoods.amount} шт"
            Glide
                .with(image)
                .load(currentGoods.photo)
                .into(image)

            popupMenu.setOnClickListener {
                val popup: PopupMenu = PopupMenu(popupMenu.context, popupMenu)
                popup.inflate(R.menu.goods_item_menu)
                popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { it ->
                    when (it.itemId) {
                        R.id.delete_item -> {
                            deleteGoods(root.context, currentGoods, holder.mGoodsViewModel)
                        }
                        R.id.archive_item -> {
                            Toast.makeText(
                                popupMenu.context,
                                "Can't archive yet",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    true
                })

                popup.show()
            }
        }
    }

    private fun deleteGoods(context: Context, goods: Goods, mGoodsViewModel: GoodsViewModel) {
       val builder = AlertDialog.Builder(context)
        builder.setPositiveButton("Yes"){_, _ ->
            mGoodsViewModel.deleteGoods(goods)
        }
        builder.setNegativeButton("No"){ _, _ ->

        }
        builder.setTitle("Delete ${goods.name}?")
        builder.setMessage("Are you sure you want to delete ${goods.name}?")
        builder.create().show()

    }

    fun setData(goods: List<Goods>){
        this.goodsList = goods
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = goodsList.size
}