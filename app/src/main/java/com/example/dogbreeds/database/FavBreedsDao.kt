package com.example.dogbreeds.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavBreedsDao {

    @Insert
    suspend fun insertFavBreed(entity: FavBreedEntity)

    @Query("DELETE FROM favBreeds WHERE imageId = :imageId")
    suspend fun deleteFavBreed(imageId: Int)

    @Query("SELECT * FROM favBreeds")
    fun getAllFavBreeds(): Flow<List<FavBreedEntity>>

    @Query("SELECT * FROM favBreeds WHERE name = :name AND subBreedName IS :subBreedName ")
    fun getAllFavByBreed(name: String, subBreedName: String?): Flow<List<FavBreedEntity>>
}