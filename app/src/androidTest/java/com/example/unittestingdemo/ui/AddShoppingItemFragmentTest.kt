package com.example.unittestingdemo.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.unittestingdemo.R
import com.example.unittestingdemo.data.local.ShoppingItem
import com.example.unittestingdemo.getOrAwaitValue
import com.example.unittestingdemo.launchFragmentInHiltContainer
import com.example.unittestingdemo.repositories.FakeShopingRespositoryAndroidTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddShoppingItemFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule= InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory:ShoppingFragmentFactory

    private lateinit var viewModel: ShoppingViewModel


    @Before
    fun setup() {
        hiltRule.inject()
        viewModel = ShoppingViewModel(FakeShopingRespositoryAndroidTest())
    }

    @Test
    fun clickInsertIntoDb_ShoppingItemInsertedIntoDb()
    {
        val testViewModel=ShoppingViewModel(FakeShopingRespositoryAndroidTest())
        launchFragmentInHiltContainer<AddShoppingItemFragment>(fragmentFactory = fragmentFactory) {

            viewModel=testViewModel
        }
        onView(withId(R.id.etShoppingItemName)).perform(replaceText("shopping Item"))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText("5"))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText("5.5"))
        onView(withId(R.id.btnAddShoppingItem)).perform(click())

        assertThat(testViewModel.shoppingItems.getOrAwaitValue()).contains(ShoppingItem("shopping Item",5,5.5f,""))
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController = mock(NavController::class.java)
        val testViewModel = ShoppingViewModel(FakeShopingRespositoryAndroidTest())
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
            viewModel.setCurImageUrl("")
        }
        pressBack()
        verify(navController).popBackStack()
        assertThat(viewModel.curImageUrl.getOrAwaitValue()).isEqualTo("")
    }

    @Test
    fun clickImage_navigateToImagePickFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.ivShoppingImage)).perform(click())

        verify(navController).navigate(
            AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
        )
    }
}