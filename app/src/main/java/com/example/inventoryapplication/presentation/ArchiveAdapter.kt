package com.example.inventoryapplication.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventoryapplication.R
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.databinding.FragmentArchiveBinding
import com.example.inventoryapplication.databinding.GoodsItemBinding

class ArchiveAdapter(private val viewModelStoreOwner: ViewModelStoreOwner):
    RecyclerView.Adapter<ArchiveAdapter.ArchiveViewHolder>() {

    private var goodsList = emptyList<Goods>()

    inner class ArchiveViewHolder(val binding: GoodsItemBinding, val mGoodsViewModel: GoodsViewModel):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchiveViewHolder {
        val binding = GoodsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val mGoodsViewModel = ViewModelProvider(viewModelStoreOwner).get(GoodsViewModel::class.java)

        return ArchiveViewHolder(binding, mGoodsViewModel)
    }

    override fun onBindViewHolder(holder: ArchiveViewHolder, position: Int) {
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
                            //deleteGoods(holder, currentGoods)
                        }
                        R.id.archive_item -> {
                            //archiveGoods(holder, currentGoods)
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

    fun setData(listGoods: List<Goods>){
        this.goodsList = listGoods
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = goodsList.size
}