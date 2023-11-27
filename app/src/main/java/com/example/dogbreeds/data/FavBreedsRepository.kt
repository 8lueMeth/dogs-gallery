package com.example.dogbreeds.data

import com.example.dogbreeds.model.FavBreed
import kotlinx.coroutines.flow.Flow

interface FavBreedsRepository {

    fun getAllFavBreeds(): Flow<List<FavBreed>>

    fun filterFavBreeds(favBreed: FavBreed): Flow<List<FavBreed>>

    suspend fun updateFavorites(favBreed: FavBreed, isLiked: Boolean)
}