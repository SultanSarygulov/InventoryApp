package com.example.inventoryapplication.domain

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.presentation.archive.ArchiveFragment
import com.example.inventoryapplication.presentation.archive.ArchiveViewModel

interface IGoods {

    fun deleteGoods(context: Context, currentGoods: Goods, viewModel: AndroidViewModel)

    fun unArchiveGoods(context: Context, currentGoods: Goods, viewModel: ArchiveViewModel)

}