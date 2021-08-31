package com.example.unittestingdemo.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.unittestingdemo.getOrAwaitValue
import com.example.unittestingdemo.launchFragmentInHiltContainer
import com.example.unittestingdemo.ui.ShoppingFragment
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

//@ExperimentalCoroutinesApi:
@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class ShopingDaoTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    //Set the rule for execute all function on same thread one function to another
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    //here using named annotation by avoiding confusion whether hilt used realdata base or test database
    @Named("test_db")
    lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        //Room.inMemoryDatabaseBuilder data stores in the RAM thats why used inMemoryDatabaseBuilder

        /*We have to create room data base in center for using hilt because
        we will same instance in every test class*/
        /*database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()*/
        hiltRule.inject()
        dao = database.shoppingDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    /*@Test
    fun testLaunchFragmentHiltContainer() {
        launchFragmentInHiltContainer<ShoppingFragment> {

        }
    }*/

    @Test
    fun insertShopingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("name", 1, 1f, "url", id = 1)
        dao.insertShoppingItem(shoppingItem)
        //Live data run asynchronousily and we dont want,so we create new class
        val allSHopingItems = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allSHopingItems).contains(shoppingItem)
    }

    @Test
    fun deleteShopingItem() =
        runBlockingTest {
            val shoppingItem =
                ShoppingItem("name", 1, 1f, "url", id = 1)
            dao.insertShoppingItem(shoppingItem)
            dao.deleteShoppingItem(shoppingItem)
            val allSHopingItems = dao.observeAllShoppingItems().getOrAwaitValue()
            assertThat(allSHopingItems).doesNotContain(shoppingItem)
        }

    @Test
    fun observeTotalPriceSum() = runBlockingTest {
        val shoppingItem1 =
            ShoppingItem("name", 2, 10f, "url", id = 1)
        val shoppingItem2 =
            ShoppingItem("name", 4, 5.5f, "url", id = 2)
        val shoppingItem3 =
            ShoppingItem("name", 0, 100f, "url", id = 3)
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)
        val totaPriceSum = dao.observeTotalPrice().getOrAwaitValue()
        assertThat(totaPriceSum).isEqualTo(2 * 10f + 4 * 5.5f + 0 * 100f)
    }

}