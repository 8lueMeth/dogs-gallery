package com.example.dogbreeds.ui.screens.favoriteimages

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.dogbreeds.ui.navigation.Screens

fun NavGraphBuilder.favoriteImagesGraph(onBackClick: () -> Unit) {
    composable(
        route = Screens.FavoriteImages.route,
    ) {
        FavoriteImagesScreen(onBackClick = onBackClick)
    }
}