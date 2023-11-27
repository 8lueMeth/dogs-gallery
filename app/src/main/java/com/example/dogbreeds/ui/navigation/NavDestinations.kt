package com.example.dogbreeds.ui.navigation

sealed class Screens(val route: String) {
    object BreedsScreen : Screens(route = "breeds")
    object BreedImages : Screens(route = "breedImages")
    object FavoriteImages : Screens(route = "favoriteImages")
}