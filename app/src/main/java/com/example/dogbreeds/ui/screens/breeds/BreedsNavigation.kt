package com.example.dogbreeds.ui.screens.breeds

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.dogbreeds.model.Breed
import com.example.dogbreeds.ui.navigation.Screens

fun NavGraphBuilder.breedsGraph(
    navigateToBreedImages: (Breed) -> Unit,
    navigateToFavoriteImages: () -> Unit,
) {
    composable(
        route = Screens.BreedsScreen.route,
    ) {
        BreedsScreen(
            navigateToBreedImages = navigateToBreedImages,
            navigateToFavoriteImages = navigateToFavoriteImages,
        )
    }
}