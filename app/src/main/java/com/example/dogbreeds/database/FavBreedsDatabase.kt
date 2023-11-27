package com.example.dogbreeds.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavBreedEntity::class],
    exportSchema = false,
    version = 1
)
abstract class FavBreedsDatabase : RoomDatabase() {
    abstract fun favBreedsDao(): FavBreedsDao
}