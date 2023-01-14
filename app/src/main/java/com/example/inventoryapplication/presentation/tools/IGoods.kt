package com.example.inventoryapplication.presentation.tools

import com.example.inventoryapplication.data.Goods

interface IGoods {

    fun onItemClick(currentGoods: Goods)

    fun onPopupMenu(currentGoods: Goods)

}