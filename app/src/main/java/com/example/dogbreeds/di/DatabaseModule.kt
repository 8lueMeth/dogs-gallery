package com.example.dogbreeds.di

import android.content.Context
import androidx.room.Room
import com.example.dogbreeds.database.FavBreedsDao
import com.example.dogbreeds.database.FavBreedsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideFavBreedsDatabase(@ApplicationContext context: Context): FavBreedsDatabase {
        return Room.databaseBuilder(
            context,
            FavBreedsDatabase::class.java,
            "favBreeds_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideFavBreedsDao(database: FavBreedsDatabase): FavBreedsDao = database.favBreedsDao()
}