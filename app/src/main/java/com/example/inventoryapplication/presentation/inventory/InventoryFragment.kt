package com.example.inventoryapplication.presentation.inventory

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.inventoryapplication.R
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.databinding.BottomSheetDialogBinding
import com.example.inventoryapplication.databinding.FragmentInventoryBinding
import com.example.inventoryapplication.presentation.IGoods
import com.example.inventoryapplication.presentation.RecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class InventoryFragment : Fragment(), IGoods, SearchView.OnQueryTextListener{

    private lateinit var mInventoryViewModel: InventoryViewModel
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

        mInventoryViewModel = ViewModelProvider(this)[InventoryViewModel::class.java]
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
        mInventoryViewModel.readAllData.observe(viewLifecycleOwner) { list ->
            list.let {
                adapter.submitList(it)
            }
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
        }

        binding.deletion.setOnClickListener {
            deleteGoods(currentGoods)
        }
        dialog.show()
    }

    fun deleteGoods(currentGoods: Goods){
        mInventoryViewModel.deleteGoods(currentGoods)
    }

    fun archiveGoods(
        currentGoods: Goods
    ) {
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


    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String): Boolean {
        if (!TextUtils.isEmpty(query)){
            searchData(query)
        } else {
            val emptyQuery = ""
            searchData(emptyQuery)
        }
        return true
    }

    private fun searchData(query: String){
        val searchQuery = "%$query%"

        mInventoryViewModel.searchData(searchQuery).observe(this) { list ->
            list.let {
                adapter.submitList(it)
            }
        }
    }
}