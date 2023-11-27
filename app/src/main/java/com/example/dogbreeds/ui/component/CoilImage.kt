package com.example.dogbreeds.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dogbreeds.R

@Composable
fun CoilImage(imageUrl: String?, modifier: Modifier = Modifier) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.placeholder),
        error = painterResource(id = R.drawable.placeholder),
        contentDescription = stringResource(id = R.string.breed_image_content_description),
        contentScale = ContentScale.Crop,
    )
}