package com.example.inventoryapplication.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.inventoryapplication.R

class ArchiveFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //(activity as AppCompatActivity).supportActionBar?.title = "Архив"
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_archive, container, false)

        return view
    }
}