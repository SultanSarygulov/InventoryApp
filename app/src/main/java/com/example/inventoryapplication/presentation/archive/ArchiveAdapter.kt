package com.example.inventoryapplication.presentation.archive

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventoryapplication.R
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.databinding.FragmentArchiveBinding
import com.example.inventoryapplication.databinding.GoodsItemBinding
import com.example.inventoryapplication.presentation.GoodsDiffCallback
import com.example.inventoryapplication.presentation.inventory.InventoryFragmentDirections
import com.example.inventoryapplication.presentation.inventory.InventoryViewModel
import kotlinx.coroutines.launch

class ArchiveAdapter(storeOwner: ViewModelStoreOwner):
    ListAdapter<Goods, ArchiveAdapter.ArchiveViewHolder>(GoodsDiffCallback()) {

    lateinit var binding: GoodsItemBinding
    private val mArchiveViewModel: ArchiveViewModel = ViewModelProvider(storeOwner).get(ArchiveViewModel::class.java)

    inner class ArchiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArchiveAdapter.ArchiveViewHolder {
        binding = GoodsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArchiveViewHolder(binding.root)
    }


    override fun onBindViewHolder(holder: ArchiveAdapter.ArchiveViewHolder, position: Int) {

        val currentGoods = getItem(position)

        binding.apply {
            name.text = currentGoods.name
            cost.text = "$ ${currentGoods.cost}"
            brand.text = currentGoods.brand
            amount.text = "${currentGoods.amount} шт"
            Glide
                .with(root)
                .load(currentGoods.photo)
                .into(image)

            popupMenu.setOnClickListener {
                val context = root.context
                val popup = PopupMenu(context, popupMenu)
                popup.inflate(R.menu.goods_item_menu)
                popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { it ->
                    when(it.itemId){
                        R.id.archive_item -> {
                            ArchiveFragment().unArchiveGoods(root.context, currentGoods, mArchiveViewModel)
                        }
                        R.id.delete_item -> {
                            val action = ArchiveFragmentDirections.actionArchiveFragmentToEditFragment(currentGoods)
                            root.findNavController().navigate(action)
                        }
                    }
                    true
                })
                popup.show()
            }
        }
    }
}