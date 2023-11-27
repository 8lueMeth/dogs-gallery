package com.example.dogbreeds.ui.screens.breedimages

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.dogbreeds.ui.navigation.Screens

fun NavGraphBuilder.breedImagesGraph(onBackClick: () -> Unit) {
    composable(
        route = Screens.BreedImages.route,
    ) {
        BreedImagesScreen(onBackClick = onBackClick)
    }
}