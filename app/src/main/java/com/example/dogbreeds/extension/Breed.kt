package com.example.dogbreeds.extension

import com.example.dogbreeds.model.Breed
import com.example.dogbreeds.model.ParentBreed
import com.example.dogbreeds.model.SubBreed

fun Breed.getPath(): String = when (this) {
    is ParentBreed -> name
    is SubBreed -> "${parentName}/${name}"
}