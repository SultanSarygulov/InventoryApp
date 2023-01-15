package com.example.inventoryapplication.presentation.tools

import com.example.inventoryapplication.domain.Goods

interface IGoods {

    fun onItemClick(currentGoods: Goods)

    fun onPopupMenu(currentGoods: Goods)

}