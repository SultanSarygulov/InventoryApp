package com.example.inventoryapplication.presentation.inventory

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventoryapplication.R
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.databinding.GoodsItemBinding
import com.example.inventoryapplication.presentation.GoodsDiffCallback
import com.example.inventoryapplication.presentation.GoodsDiffUtilCallback

class InventoryAdapter(private val viewModelStoreOwner: ViewModelStoreOwner):
    RecyclerView.Adapter<InventoryAdapter.ViewHolder>(){

    lateinit var binding: GoodsItemBinding
    lateinit var mInventoryViewModel: InventoryViewModel

    private var goodsList = mutableListOf<Goods>()
    fun setList(goodses: List<Goods>, context: Context){
        val diffCallback = GoodsDiffUtilCallback(goodsList, goodses)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        goodsList.clear()
        goodsList.addAll(goodses)
        diffResult.dispatchUpdatesTo(this)
        Log.d("InventoryAdapter","${goodsList}")
    }

    override fun getItemCount(): Int = goodsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryAdapter.ViewHolder {
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.goods_item, parent, false)
        binding = GoodsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        mInventoryViewModel = ViewModelProvider(viewModelStoreOwner).get(InventoryViewModel::class.java)

        return InventoryAdapter.ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: InventoryAdapter.ViewHolder, position: Int)  {

        val currentGoods = goodsList[position]

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
                            //val action = InventoryFragmentDirections.actionInventoryFragmentToEditFragment(currentGoods)
                            //holder.binding.root.findNavController().navigate(action)
                        }
                    }
                    true
                })
                popup.show()
            }

        }
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

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
            Toast.makeText(context, "'${currentGoods.name}' архивирован!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "'${currentGoods.name}' уже архивирован", Toast.LENGTH_LONG).show()
        }

    }

    private fun deleteGoods(context: Context, currentGoods: Goods, mInventoryViewModel: InventoryViewModel) {
        // context = holder.binding.root.context
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton("Yes"){_, _ ->
            Toast.makeText(context, "Succesfully deleted!", Toast.LENGTH_LONG)
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