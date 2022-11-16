package com.example.inventoryapplication.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.inventoryapplication.R
import com.example.inventoryapplication.databinding.FragmentArchiveBinding
import com.example.inventoryapplication.presentation.ArchiveAdapter
import com.example.inventoryapplication.presentation.GoodsViewModel

class ArchiveFragment : Fragment() {

    private lateinit var binding: FragmentArchiveBinding
    private lateinit var mGoodsViewModel: GoodsViewModel
    private val adapter = ArchiveAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentArchiveBinding.inflate(inflater, container, false)

        mGoodsViewModel = ViewModelProvider(this).get(GoodsViewModel::class.java)

        mGoodsViewModel.readArchivedData.observe(viewLifecycleOwner, Observer { goods ->
            adapter.setData(goods)
        })

        return binding.root
    }
}