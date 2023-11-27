package com.example.dogbreeds.data

import com.example.dogbreeds.model.Breed
import com.example.dogbreeds.model.ParentBreed

interface BreedsRepository {

    suspend fun getAllBreeds(): Result<List<ParentBreed>>

    suspend fun getAllBreedImages(breed: Breed): Result<List<String>>

    suspend fun getRandomImageByBreed(breed: Breed): Result<String>
}