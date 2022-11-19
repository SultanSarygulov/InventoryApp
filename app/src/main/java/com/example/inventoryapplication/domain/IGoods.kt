package com.example.inventoryapplication.domain

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.presentation.archive.ArchiveFragment
import com.example.inventoryapplication.presentation.archive.ArchiveViewModel

interface IGoods {

    fun onItemClick(currentGoods: Goods)

    fun onPopupMenu(currentGoods: Goods)

}