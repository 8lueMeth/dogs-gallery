package com.example.dogbreeds.di

import com.example.dogbreeds.data.BreedsRepository
import com.example.dogbreeds.data.BreedsRepositoryImpl
import com.example.dogbreeds.data.FavBreedsRepository
import com.example.dogbreeds.data.FavBreedsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideBreedsRepository(impl: BreedsRepositoryImpl): BreedsRepository

    @Binds
    @Singleton
    abstract fun provideFavBreedsRepository(impl: FavBreedsRepositoryImpl): FavBreedsRepository
}