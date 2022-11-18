package com.example.inventoryapplication.presentation.archive

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.inventoryapplication.R
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.databinding.FragmentArchiveBinding
import com.example.inventoryapplication.databinding.GoodsItemBinding
import com.example.inventoryapplication.domain.IGoods
import com.example.inventoryapplication.presentation.inventory.InventoryViewModel
import kotlinx.coroutines.launch

class ArchiveFragment : Fragment(), IGoods {

    private lateinit var binding: FragmentArchiveBinding
    private lateinit var mArchiveViewModel: ArchiveViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentArchiveBinding.inflate(inflater, container, false)

        val adapter = ArchiveAdapter(this)
        binding.archiveRecycler.layoutManager = GridLayoutManager(this.context, 2)
        binding.archiveRecycler.adapter = adapter

        mArchiveViewModel = ViewModelProvider(this)[ArchiveViewModel::class.java]
        mArchiveViewModel.readArchivedData.observe(viewLifecycleOwner){ list ->
            adapter.submitList(list)
        }
        return binding.root
    }


    override fun deleteGoods(context: Context, currentGoods: Goods, viewModel: AndroidViewModel) {
        TODO("Not yet implemented")
    }

    override fun unArchiveGoods(
        context: Context,
        currentGoods: Goods,
        viewModel: ArchiveViewModel
    ) {
        if (currentGoods.archived){
            val archivedTrue = false

            val unArchivedGoods = Goods(
                currentGoods.id,
                currentGoods.name,
                currentGoods.cost,
                currentGoods.brand,
                currentGoods.amount,
                currentGoods.photo,
                archivedTrue)

                viewModel.updateGoods(unArchivedGoods)


            Toast.makeText(context, "'${currentGoods.name}' восставновлен!", Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(context, "'${currentGoods.name}' уже восставновлен", Toast.LENGTH_LONG).show()
        }
    }

}