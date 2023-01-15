package com.example.inventoryapplication.presentation.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventoryapplication.domain.Goods
import com.example.inventoryapplication.domain.GoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditViewModel
@Inject
constructor(private val repository: GoodsRepository): ViewModel() {

    fun editGoods(goods: Goods){
        viewModelScope.launch(Dispatchers.IO ) {
            repository.editGoods(goods)
        }
    }
}