package com.example.dogbreeds.ui.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.dogbreeds.extension.navigateWithArgs
import com.example.dogbreeds.ui.screens.breedimages.breedImagesGraph
import com.example.dogbreeds.ui.screens.breeds.breedsGraph
import com.example.dogbreeds.ui.screens.favoriteimages.favoriteImagesGraph

@Composable
fun DogBreedsNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.BreedsScreen.route,
        builder = {
            breedsGraph(
                navigateToBreedImages = { breed ->
                    val args = Bundle().apply {
                        putParcelable(
                            "breed",
                            breed
                        )
                    }
                    navController.navigateWithArgs(
                        route = Screens.BreedImages.route,
                        args = args
                    )
                },
                navigateToFavoriteImages = {
                    navController.navigate(route = Screens.FavoriteImages.route)
                }
            )
            breedImagesGraph(onBackClick = { navController.popBackStack() })
            favoriteImagesGraph(onBackClick = { navController.popBackStack() })
        }
    )
}