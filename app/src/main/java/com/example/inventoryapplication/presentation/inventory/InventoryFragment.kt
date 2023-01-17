package com.example.inventoryapplication.presentation.inventory

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.inventoryapplication.R
import com.example.inventoryapplication.domain.Goods
import com.example.inventoryapplication.databinding.BottomSheetDialogBinding
import com.example.inventoryapplication.databinding.FragmentInventoryBinding
import com.example.inventoryapplication.presentation.tools.IGoods
import com.example.inventoryapplication.presentation.RecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InventoryFragment : Fragment(), IGoods, SearchView.OnQueryTextListener{

    private val mInventoryViewModel: InventoryViewModel by viewModels()
    private lateinit var binding: FragmentInventoryBinding
    private lateinit var adapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentInventoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setLiveDataObserver()

        // Navigate to Add fragment
        binding.addButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_inventoryFragment_to_addFragment)
        }

        // Set SearchBar
        binding.searchBar.isSubmitButtonEnabled = false
        binding.searchBar.setOnQueryTextListener(this)
    }

    private fun setRecyclerView() {
        binding.recyclerview.layoutManager = GridLayoutManager(this.context, 2)
        adapter = RecyclerAdapter(this)
        binding.recyclerview.adapter = adapter
    }


    private fun setLiveDataObserver() {

        mInventoryViewModel.goodsList.observe(viewLifecycleOwner) {
            adapter.modifyList(it)
        }

    }

    override fun onItemClick(currentGoods: Goods) {
        val action = InventoryFragmentDirections.actionInventoryFragmentToEditFragment(currentGoods)
        findNavController().navigate(action)
    }

    override fun onPopupMenu(currentGoods: Goods) {
        val binding = BottomSheetDialogBinding.inflate(LayoutInflater.from(context))
        val dialog = BottomSheetDialog(requireContext())
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        binding.archivation.text = "Архивировать"
        binding.deletion.text = "Удалить"

        binding.archivation.setOnClickListener {
            archiveGoods(currentGoods)
            dialog.dismiss()
        }

        binding.deletion.setOnClickListener {
            deleteGoods(currentGoods)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun deleteGoods(currentGoods: Goods){
        mInventoryViewModel.deleteGoods(currentGoods)
    }

    private fun archiveGoods(currentGoods: Goods) {
        if (!currentGoods.archived){

            mInventoryViewModel.archiveGoods(currentGoods)

            Toast.makeText(context, "'${currentGoods.name}' архивирован!", Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(context, "'${currentGoods.name}' уже архивирован", Toast.LENGTH_LONG).show()
        }
    }


    override fun onQueryTextSubmit(query: String): Boolean {

        return false
    }

    override fun onQueryTextChange(query: String): Boolean {
        adapter.filter(query)
        return true
    }

}