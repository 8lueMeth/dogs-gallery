package com.example.dogbreeds.ui.component

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun InitialLoadLoading(modifier: Modifier) {
    CircularProgressIndicator(
        color = MaterialTheme.colors.secondary,
        modifier = modifier
    )
}