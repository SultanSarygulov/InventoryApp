package com.example.inventoryapplication.presentation

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.drawToBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.inventoryapplication.R
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.databinding.FragmentAddBinding
import com.example.inventoryapplication.presentation.inventory.InventoryViewModel
import kotlinx.coroutines.launch

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var mInventoryViewModel: InventoryViewModel

    companion object{
        const val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //(activity as AppCompatActivity).supportActionBar?.title = "Добавить товар"
        // Inflate the layout for this fragment

        mInventoryViewModel = ViewModelProvider(this).get(InventoryViewModel::class.java)

        binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.addImageButton.setOnClickListener{
            getImageFromGallery()
        }

        binding.cancelBtn.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_addFragment_to_inventoryFragment)
        }


        binding.addBtn.setOnClickListener {
            addGoods()
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

    private fun addGoods() {
        val name = binding.goodsNameEdit.text.toString()
        val cost = binding.goodsCostEdit.text.toString()
        val brand = binding.goodsBrandEdit.text.toString()
        val amount = binding.goodsAmountEdit.text.toString()
        val photo = binding.addImageButton.drawToBitmap()

        if(inputCheck(name, cost, brand, amount)){
                // Create Goods
                val goods = Goods(0, name, Integer.parseInt(cost), brand, Integer.parseInt(amount), photo, false)
                addToDatabase(goods)

        } else {
            Toast.makeText(requireContext(), "Please, fill out all fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun addToDatabase(goods: Goods) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){ _,_ ->
            mInventoryViewModel.addGoods(goods)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            // Navigate back
            findNavController().navigate(R.id.action_addFragment_to_inventoryFragment)
        }
        builder.setNegativeButton("No"){_, _ ->

        }
        builder.setTitle("Добавить ${goods.name} в инвентарь?")
        builder.create().show()
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
