package com.example.inventoryapplication.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.inventoryapplication.databinding.FragmentArchiveBinding
import com.example.inventoryapplication.presentation.ArchiveAdapter
import com.example.inventoryapplication.presentation.InventoryViewModel

class ArchiveFragment : Fragment() {

    private lateinit var binding: FragmentArchiveBinding
    private lateinit var mInventoryViewModel: InventoryViewModel
    private val adapter = ArchiveAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentArchiveBinding.inflate(inflater, container, false)

        mInventoryViewModel = ViewModelProvider(this).get(InventoryViewModel::class.java)

        return binding.root
    }
}