package com.example.inventoryapplication.presentation.inventory

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventoryapplication.R
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.databinding.GoodsItemBinding
import com.example.inventoryapplication.presentation.GoodsDiffCallback

class InventoryAdapter(private val viewModelStoreOwner: ViewModelStoreOwner):
    ListAdapter<Goods, InventoryAdapter.ViewHolder>(GoodsDiffCallback()) {

    lateinit var binding: GoodsItemBinding
    lateinit var mInventoryViewModel: InventoryViewModel

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.goods_item, parent, false)
        binding = GoodsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        mInventoryViewModel = ViewModelProvider(viewModelStoreOwner).get(InventoryViewModel::class.java)

        return InventoryAdapter.ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  {

        val currentGoods = getItem(position)

        binding.apply {
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
                            deleteGoods(binding.root.context, currentGoods, mInventoryViewModel)
                        }
                        R.id.archive_item -> {
                            archiveGoods(binding.root.context, currentGoods, mInventoryViewModel)
                        }
                    }
                    true
                })
                popup.show()
            }

        }
    }



    private fun archiveGoods(context: Context, currentGoods: Goods, mInventoryViewModel: InventoryViewModel) {
        //val context = holder.binding.root.context
        if (!currentGoods.archived){
            val archivedTrue = true

            val archivedGoods = Goods(
                currentGoods.id,
                currentGoods.name,
                currentGoods.cost,
                currentGoods.brand,
                currentGoods.amount,
                currentGoods.photo,
                archivedTrue)

            mInventoryViewModel.updateGoods(archivedGoods)
            Toast.makeText(context, "'${currentGoods.name}' архивирован!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "'${currentGoods.name}' уже архивирован", Toast.LENGTH_SHORT).show()
        }

    }

    private fun deleteGoods(context: Context, currentGoods: Goods, mInventoryViewModel: InventoryViewModel) {
        // context = holder.binding.root.context
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton("Yes"){_, _ ->
            Toast.makeText(context, "Succesfully deleted!", Toast.LENGTH_SHORT)
            mInventoryViewModel.deleteGoods(currentGoods)
        }
        builder.setNegativeButton("No"){ _, _ ->    }
        builder.setTitle("Удалить ${currentGoods.name}?")
        builder.create().show()

    }

//    fun setData(goods: MutableList<Goods>, context: Context){
//        this.currentList = goods
//        //Toast.makeText(context, "GoodsList: ${goods}", Toast.LENGTH_LONG).show()
//        notifyDataSetChanged()
//    }


}