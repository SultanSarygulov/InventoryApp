package com.example.inventoryapplication.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.inventoryapplication.domain.Goods
import com.example.inventoryapplication.domain.GoodsRepository
import javax.inject.Inject

class GoodsRepositoryImpl
    @Inject
    constructor(private val goodsDao: GoodsDao) : GoodsRepository {

    private val goodsListLiveData = goodsDao.readAllData()

    private val archivedGoodsListLiveData = goodsDao.readArchivedData()

    override suspend fun addGoods(goods: Goods) {
        goodsDao.addGoods(goods)
    }

    override suspend fun archiveGoods(goods: Goods) {

        val archivedGoods = Goods(
            goods.id,
            goods.name,
            goods.cost,
            goods.brand,
            goods.amount,
            goods.photo,
            !goods.archived)

        editGoods(archivedGoods)
    }

    override suspend fun deleteGoods(goods: Goods) {
        goodsDao.deleteGoods(goods)
    }

    override suspend fun editGoods(goods: Goods) {
        goodsDao.editGoods(goods)
    }

    override fun getGoodsList(): LiveData<MutableList<Goods>> {
        return goodsListLiveData
    }

    override fun getArchivedGoodsList(): LiveData<MutableList<Goods>> {
        return archivedGoodsListLiveData
    }
}