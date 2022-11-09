package com.example.inventoryapplication.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.inventoryapplication.R

class AddFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //(activity as AppCompatActivity).supportActionBar?.title = "Добавить товар"
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_add, container, false)

        val cancelButton: Button = view.findViewById(R.id.cancelBtn)
        cancelButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_addFragment_to_inventoryFragment)
        }

        return view
    }


}