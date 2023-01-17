package com.example.inventoryapplication.di

import android.content.Context
import com.example.inventoryapplication.data.GoodsDao
import com.example.inventoryapplication.data.GoodsDatabase
import com.example.inventoryapplication.data.GoodsRepositoryImpl
import com.example.inventoryapplication.domain.GoodsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGoodsDao(@ApplicationContext context: Context): GoodsDao{
        return GoodsDatabase.getDatabase(context).goodsDao()
    }

    @Singleton
    @Provides
    fun provideRepository(goodsDao: GoodsDao): GoodsRepository{
        return GoodsRepositoryImpl(goodsDao)
    }
}