package com.example.inventoryapplication.presentation.inventory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.inventoryapplication.R
import com.example.inventoryapplication.databinding.FragmentInventoryBinding

class InventoryFragment : Fragment()/*, SearchView.OnQueryTextListener*/ {

    private lateinit var mInventoryViewModel: InventoryViewModel
    private lateinit var binding: FragmentInventoryBinding
    private lateinit var adapter: InventoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //(activity as AppCompatActivity).supportActionBar?.title = "Главное"

        binding = FragmentInventoryBinding.inflate(inflater, container, false)

        // RecyclerView and connect it with Adapter
        binding.recyclerview.layoutManager = GridLayoutManager(this.context, 2)
        adapter = InventoryAdapter(this)
        binding.recyclerview.adapter = adapter

        // Navigate to Add fragment
        binding.addButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_inventoryFragment_to_addFragment)
        }

        // Set SearchBar
//        binding.searchBar.isSubmitButtonEnabled = true
//        binding.searchBar.setOnQueryTextListener(this)

        // Set ViewModel
        mInventoryViewModel = ViewModelProvider(this).get(InventoryViewModel::class.java)
        mInventoryViewModel.readAllData.observe(viewLifecycleOwner) { list ->
            list.let {
                adapter.setList(it, requireContext())
            }
        }

        return binding.root
    }

//    override fun onQueryTextSubmit(query: String?): Boolean {
//        return true
//    }
//
//    override fun onQueryTextChange(query: String?): Boolean {
//        if (query != null){
//            searchData(query)
//        }
//        return true
//    }

//    private fun searchData(query: String){
//        val searchQuery = "%$query%"
//
//        mInventoryViewModel.searchData(searchQuery).observe(this) { list ->
//            list.let {
//                adapter.setList(it, requireContext())
//            }
//        }
//    }
}