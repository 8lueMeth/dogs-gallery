package com.example.dogbreeds.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class IODispatcher

@Module
@InstallIn(SingletonComponent::class)
class AnnotationModule {

    @IODispatcher
    @Singleton
    @Provides
    fun providesIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}