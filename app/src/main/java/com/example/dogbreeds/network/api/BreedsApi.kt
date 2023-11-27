package com.example.dogbreeds.network.api

import com.example.dogbreeds.network.model.BreedsResponse
import com.example.dogbreeds.network.model.ImagesResponse
import com.example.dogbreeds.network.model.RandomImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BreedsApi {

    @GET("breeds/list/all")
    suspend fun getAllBreeds(): Response<BreedsResponse>

    @GET("breed/{breed}/images")
    suspend fun getAllBreedImages(
        @Path("breed", encoded = true) breed: String
    ): Response<ImagesResponse>

    @GET("breed/{breed}/images/random")
    suspend fun getRandomImageByBreed(
        @Path("breed", encoded = true) breed: String
    ): Response<RandomImageResponse>
}