package com.example.dogbreeds.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.dogbreeds.ui.navigation.DogBreedsNavHost
import com.example.dogbreeds.ui.theme.DogBreedsTheme

@Composable
fun DogBreedsApp() {
    DogBreedsTheme {
        val navController = rememberNavController()
        DogBreedsNavHost(navController = navController)
    }
}