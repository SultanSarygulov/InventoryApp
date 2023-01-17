package com.example.inventoryapplication.presentation.inventory

import androidx.lifecycle.*
import com.example.inventoryapplication.domain.Goods
import com.example.inventoryapplication.domain.GoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel
@Inject
constructor(private val repository: GoodsRepository): ViewModel() {

    val goodsList = repository.getGoodsList()

    fun deleteGoods(goods: Goods){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteGoods(goods)
        }
    }

    fun archiveGoods(goods: Goods){
        viewModelScope.launch(Dispatchers.IO) {
            repository.archiveGoods(goods)
        }
    }
}