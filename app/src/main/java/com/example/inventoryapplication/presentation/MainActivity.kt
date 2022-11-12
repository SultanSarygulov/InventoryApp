package com.example.inventoryapplication.presentation

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.inventoryapplication.R
import com.example.inventoryapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(getResources().getColor(R.color.white)))

        val navController = Navigation.findNavController(this, R.id.nav_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}