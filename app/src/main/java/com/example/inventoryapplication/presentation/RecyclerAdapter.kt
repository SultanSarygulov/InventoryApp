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
import androidx.fragment.app.ListFragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.inventoryapplication.R
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.databinding.GoodsItemBinding
import com.example.inventoryapplication.presentation.fragments.InventoryFragmentDirections

class RecyclerAdapter(private val viewModelStoreOwner: ViewModelStoreOwner, private val lifecycleOwner: LifecycleOwner): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var goodsList = emptyList<Goods>()

    class ViewHolder(val binding: GoodsItemBinding, val mGoodsViewModel: GoodsViewModel):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.goods_item, parent, false)
        val binding = GoodsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val mGoodsViewModel = ViewModelProvider(viewModelStoreOwner).get(GoodsViewModel::class.java)

        return ViewHolder(binding, mGoodsViewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var currentGoods = goodsList[position]
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
                            deleteGoods(holder, currentGoods)
                        }
                        R.id.archive_item -> {
                            archiveGoods(holder, currentGoods)
//                           val action = InventoryFragmentDirections.actionInventoryFragmentToEditFragment(currentGoods)
//                           holder.binding.root.findNavController().navigate(action)
                        }
                    }
                    true
                })

                popup.show()
            }
        }
    }

    private fun archiveGoods(holder: ViewHolder, currentGoods: Goods) {
        val context = holder.binding.root.context

        val archivedTrue = true

        val archivedGoods = Goods(
            currentGoods.id,
            currentGoods.name,
            currentGoods.cost,
            currentGoods.brand,
            currentGoods.amount,
            currentGoods.photo,
            archivedTrue)

        //goodsList.drop(goodsList.indexOf(currentGoods))

        holder.mGoodsViewModel.updateGoods(archivedGoods)
        holder.mGoodsViewModel.readAllData.observe(lifecycleOwner){goods ->
            goods.drop(goods.indexOf(currentGoods))
        }
        notifyDataSetChanged()
        Toast.makeText(context, "'${currentGoods.name}' архивирован!", Toast.LENGTH_LONG).show()
    }

    private fun deleteGoods(holder: ViewHolder, currentGoods: Goods ) {
        val context = holder.binding.root.context
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton("Yes"){_, _ ->
            //Toast.makeText(context, "Succesfully deleted!", Toast.LENGTH_LONG)
            Toast.makeText(context, "${goodsList.size}", Toast.LENGTH_LONG).show()
            holder.mGoodsViewModel.deleteGoods(currentGoods)
        }
        builder.setNegativeButton("No"){ _, _ ->    }
        builder.setTitle("Удалить ${currentGoods.name}?")
        builder.create().show()

    }

    fun setData(goods: List<Goods>, context: Context){

        var readAllDataGoods = goods

        this.goodsList = goods
        Toast.makeText(context, "${goods}", Toast.LENGTH_LONG).show()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = goodsList.size
}