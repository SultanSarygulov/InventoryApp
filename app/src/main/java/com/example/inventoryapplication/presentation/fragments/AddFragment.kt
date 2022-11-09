package com.example.inventoryapplication.presentation.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.inventoryapplication.R
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.databinding.FragmentAddBinding
import com.example.inventoryapplication.presentation.GoodsViewModel

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var mGoodsViewModel: GoodsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //(activity as AppCompatActivity).supportActionBar?.title = "Добавить товар"
        // Inflate the layout for this fragment

        mGoodsViewModel = ViewModelProvider(this).get(GoodsViewModel::class.java)

        binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.cancelBtn.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_addFragment_to_inventoryFragment)
        }

        binding.addBtn.setOnClickListener {
            addToDatabase()
        }

        return binding.root
    }

    private fun addToDatabase() {
        val name = binding.goodsNameEdit.text.toString()
        val cost = binding.goodsCostEdit.text.toString()
        val brand = binding.goodsBrandEdit.text.toString()
        val amount = binding.goodsAmountEdit.text.toString()

        if(inputCheck(name, cost, brand, amount)){
            // create Goods
            val goods = Goods(0, name, Integer.parseInt(cost), brand, Integer.parseInt(amount))
            // use add function from viewmodel
            mGoodsViewModel.addGoods(goods)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            // Navigate back
            findNavController().navigate(R.id.action_addFragment_to_inventoryFragment)
        } else {
            Toast.makeText(requireContext(), "${inputCheck(name, cost, brand, amount)}", Toast.LENGTH_LONG).show()
        }


    }
    // Check if the user inputted info
    private fun inputCheck(name: String, cost: String, brand: String, amount: String): Boolean {
        return !(
                TextUtils.isEmpty(name) && TextUtils.isEmpty(cost) && TextUtils.isEmpty(brand) && TextUtils.isEmpty(amount)
                )
    }


}