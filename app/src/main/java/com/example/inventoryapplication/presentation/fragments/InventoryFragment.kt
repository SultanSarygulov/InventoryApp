package com.example.inventoryapplication.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class InventoryFragment : Fragment() {

    private lateinit var mGoodsViewModel: GoodsViewModel
    //private lateinit var binding: FragmentInventoryBinding
    private val adapter = RecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //(activity as AppCompatActivity).supportActionBar?.title = "Главное"
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_inventory, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = GridLayoutManager(this.context, 2)
        recyclerView.adapter = adapter

        mGoodsViewModel = ViewModelProvider(this).get(GoodsViewModel::class.java)
        mGoodsViewModel.readAllData.observe(viewLifecycleOwner, Observer{goods ->
            adapter.setData(goods)
        })

        val addButton: FloatingActionButton = view.findViewById(R.id.addButton)
        addButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_inventoryFragment_to_addFragment)
        }

        return view
    }
}