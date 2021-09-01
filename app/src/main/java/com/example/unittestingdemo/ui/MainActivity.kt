package com.example.unittestingdemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.unittestingdemo.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/* for reference
https://github.com/philipplackner/ShoppingListTestingYT*/


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_main)
    }
}