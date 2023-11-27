package com.example.dogbreeds.network.model

import com.example.dogbreeds.model.ParentBreed
import com.example.dogbreeds.model.SubBreed

data class BreedsResponse(
    val message: Map<String, List<String>>,
    val status: String,
)

fun BreedsResponse.asExternalModel(): List<ParentBreed> =
    message.map { entry ->
        ParentBreed(
            name = entry.key,
            subBreeds = entry.value.map { it.asSubBreed(entry.key) }
        )
    }

private fun String.asSubBreed(parentName: String) = SubBreed(name = this, parentName = parentName)