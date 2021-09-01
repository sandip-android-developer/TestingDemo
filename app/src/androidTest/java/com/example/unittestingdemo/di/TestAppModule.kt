package com.example.unittestingdemo.di

import android.content.Context
import androidx.room.Room
import com.example.unittestingdemo.data.local.ShoppingItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    /*Here we are not creating singleton because we need every time new instance in test*/
    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context)=Room.inMemoryDatabaseBuilder(context,ShoppingItemDatabase::class.java)
        .allowMainThreadQueries()
        .build()
}