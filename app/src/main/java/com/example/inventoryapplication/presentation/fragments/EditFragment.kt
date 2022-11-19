package com.example.inventoryapplication.presentation.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.drawToBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventoryapplication.R
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.databinding.FragmentEditBinding
import com.example.inventoryapplication.presentation.archive.ArchiveFragmentDirections
import com.example.inventoryapplication.presentation.archive.ArchiveViewModel
import com.example.inventoryapplication.presentation.inventory.InventoryViewModel

class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private lateinit var mArchiveViewModel: ArchiveViewModel
    private val args by navArgs<EditFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(layoutInflater, container, false)

        mArchiveViewModel = ViewModelProvider(this).get(ArchiveViewModel::class.java)

        binding.updateImageButton.setImageBitmap(args.currentGoods.photo)
        binding.updateGoodsNameEdit.setText(args.currentGoods.name)
        binding.updateGoodsCostEdit.setText(args.currentGoods.cost.toString())
        binding.updateGoodsBrandEdit.setText(args.currentGoods.brand)
        binding.updateGoodsAmountEdit.setText(args.currentGoods.amount.toString())

        binding.updateImageButton.setOnClickListener {
            getImageFromGallery()
        }

        binding.updateBtn.setOnClickListener {
            updateGoods()
        }

        binding.cancelUpdateBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    private fun getImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent ,69)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 69 && resultCode == Activity.RESULT_OK){
            val imagePath: Uri? = data?.data
            binding.updateImageButton.setImageURI(imagePath)
        }
    }

    private fun updateGoods() {
        val image = binding.updateImageButton.drawToBitmap()
        val name = binding.updateGoodsNameEdit.text.toString()
        val cost = binding.updateGoodsCostEdit.text.toString()
        val brand = binding.updateGoodsBrandEdit.text.toString()
        val amount = binding.updateGoodsAmountEdit.text.toString()

        if (inputCheck(name, cost, brand, amount)){
            val updatedGoods: Goods = Goods(args.currentGoods.id, name, Integer.parseInt(cost), brand, Integer.parseInt(amount), image, true)
            mArchiveViewModel.updateGoods(updatedGoods)
            Toast.makeText(requireContext(), "Изменения сохранены!", Toast.LENGTH_LONG).show()
            findNavController().navigateUp()
        } else {
            Toast.makeText(requireContext(), "Заполните все поля!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(name: String, cost: String, brand: String, amount: String): Boolean {
        return (
                !TextUtils.isEmpty(name) &&
                !TextUtils.isEmpty(cost) &&
                !TextUtils.isEmpty(brand) &&
                !TextUtils.isEmpty(amount)
                )
    }
}