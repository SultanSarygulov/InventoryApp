package com.example.inventoryapplication.presentation.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.inventoryapplication.R
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.databinding.FragmentAddBinding
import com.example.inventoryapplication.presentation.GoodsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URI

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var mGoodsViewModel: GoodsViewModel

    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //(activity as AppCompatActivity).supportActionBar?.title = "Добавить товар"
        // Inflate the layout for this fragment

        mGoodsViewModel = ViewModelProvider(this).get(GoodsViewModel::class.java)

        binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.addImageButton.setOnClickListener{
            getImageFromGallery()
        }

        binding.cancelBtn.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_addFragment_to_inventoryFragment)
        }


        binding.addBtn.setOnClickListener {
            addToDatabase()
        }


        return binding.root
    }

    // Upload Image
    private fun getImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    // Set Image to ImageButton
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            val imagePath: Uri? = data?.data
            val imageBitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imagePath);
            binding.addImageButton.setImageURI(imagePath)
        }
    }

    private suspend fun getBitmap(photoPath: Drawable): Bitmap{
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data(photoPath)
            .crossfade(true)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    private fun addToDatabase() {
        val name = binding.goodsNameEdit.text.toString()
        val cost = binding.goodsCostEdit.text.toString()
        val brand = binding.goodsBrandEdit.text.toString()
        val amount = binding.goodsAmountEdit.text.toString()
        val photo = binding.addImageButton.getDrawable()

        if(inputCheck(name, cost, brand, amount)){
            lifecycleScope.launch{
                // Create Goods
                val goods = Goods(0, name, Integer.parseInt(cost), brand, Integer.parseInt(amount), getBitmap(photo))
                // use add function from viewmodel
                mGoodsViewModel.addGoods(goods)
                Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
                // Navigate back
                findNavController().navigate(R.id.action_addFragment_to_inventoryFragment)
            }
        } else {
            Toast.makeText(requireContext(), "Please, fill out all fields", Toast.LENGTH_LONG).show()
        }


    }
    // Check if the user inputted info
    private fun inputCheck(name: String, cost: String, brand: String, amount: String): Boolean {
        return (
                !TextUtils.isEmpty(name) &&
                !TextUtils.isEmpty(cost) &&
                !TextUtils.isEmpty(brand) &&
                !TextUtils.isEmpty(amount)
                )
    }


}