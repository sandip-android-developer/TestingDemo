package com.example.unittestingdemo.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unittestingdemo.data.local.ShoppingItem
import com.example.unittestingdemo.data.remote.response.ImageResponse
import com.example.unittestingdemo.other.Resource

class FakeShopingRespository : ShoppingRepository {
    private val shopingItems = mutableListOf<ShoppingItem>()
    private val observalShopingItem = MutableLiveData<List<ShoppingItem>>(shopingItems)
    private val observalTotalPrice = MutableLiveData<Float>()
    private var shouldRetrunNetworkError = false

    private fun refreshLiveData() {
        observalShopingItem.postValue(shopingItems)
        observalTotalPrice.postValue(getTotalPrice())
    }

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldRetrunNetworkError = value
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shopingItems.add(shoppingItem)
        refreshLiveData()

    }

    private fun getTotalPrice(): Float {
        return shopingItems.sumByDouble { it.price.toDouble() }.toFloat()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shopingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return observalShopingItem
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observalTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return if (shouldRetrunNetworkError) {
            Resource.error("error", null)
        } else {
            Resource.success(ImageResponse(listOf(), 0, 0))
        }
    }
}