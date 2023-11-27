package com.example.dogbreeds.data

import com.example.dogbreeds.data.util.result
import com.example.dogbreeds.data.util.runCatchingWithContext
import com.example.dogbreeds.di.IODispatcher
import com.example.dogbreeds.extension.getPath
import com.example.dogbreeds.model.Breed
import com.example.dogbreeds.model.ParentBreed
import com.example.dogbreeds.network.api.BreedsApi
import com.example.dogbreeds.network.model.asExternalModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class BreedsRepositoryImpl @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val breedsApi: BreedsApi,
) : BreedsRepository {

    override suspend fun getAllBreeds(): Result<List<ParentBreed>> =
        runCatchingWithContext(dispatcher) {
            breedsApi.getAllBreeds()
        }.result {
            it.asExternalModel()
        }

    override suspend fun getAllBreedImages(breed: Breed): Result<List<String>> =
        runCatchingWithContext(dispatcher) {
            breedsApi.getAllBreedImages(breed.getPath())
        }.result { it.message }

    override suspend fun getRandomImageByBreed(breed: Breed): Result<String> {
        return runCatchingWithContext(dispatcher) {
            breedsApi.getRandomImageByBreed(breed.getPath())
        }.result { it.message }
    }
}