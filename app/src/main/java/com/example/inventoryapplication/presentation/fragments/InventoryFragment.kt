package com.example.inventoryapplication.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inventoryapplication.R
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.databinding.FragmentInventoryBinding
import com.example.inventoryapplication.presentation.GoodsViewModel
import com.example.inventoryapplication.presentation.RecyclerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class InventoryFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var mGoodsViewModel: GoodsViewModel
    private lateinit var binding: FragmentInventoryBinding
    private lateinit var adapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //(activity as AppCompatActivity).supportActionBar?.title = "Главное"
        // Inflate the layout for this fragment
        binding = FragmentInventoryBinding.inflate(inflater, container, false)

        binding.recyclerview.layoutManager = GridLayoutManager(this.context, 2)
        adapter = RecyclerAdapter(this, viewLifecycleOwner)
        binding.recyclerview.adapter = adapter

        binding.addButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_inventoryFragment_to_addFragment)
        }

        binding.searchBar.isSubmitButtonEnabled = true
        binding.searchBar.setOnQueryTextListener(this)


        mGoodsViewModel = ViewModelProvider(this).get(GoodsViewModel::class.java)
        mGoodsViewModel.readAllData.observe(viewLifecycleOwner) { list ->
            list.let {
                adapter.setData(it, requireContext())
            }
        }

        return binding.root
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null){
            searchData(query)
        }
        return true
    }

    private fun searchData(query: String){
        val searchQuery = "%$query%"

        mGoodsViewModel.searchData(searchQuery).observe(this) { list ->
            list.let {
                adapter.setData(it, requireContext())
            }
        }
    }
}