package com.example.unittestingdemo.repositories

import androidx.lifecycle.LiveData
import com.example.unittestingdemo.data.local.ShoppingItem
import com.example.unittestingdemo.data.remote.response.ImageResponse
import com.example.unittestingdemo.other.Resource

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

     fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}