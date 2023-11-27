package com.example.dogbreeds.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favBreeds")
data class FavBreedEntity(
    @PrimaryKey
    val imageId: Int,
    val name: String,
    val subBreedName: String?,
    val imageUrl: String,
)