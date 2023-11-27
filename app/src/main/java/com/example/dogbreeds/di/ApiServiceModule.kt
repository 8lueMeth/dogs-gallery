package com.example.dogbreeds.di

import com.example.dogbreeds.network.api.BreedsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiServiceModule {

    @Singleton
    @Provides
    fun providePlacesApiService(retrofit: Retrofit): BreedsApi =
        retrofit.create(BreedsApi::class.java)
}