package com.example.dogbreeds.ui.screens.breedimages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dogbreeds.R
import com.example.dogbreeds.model.FavBreed
import com.example.dogbreeds.ui.component.AppBarBackButton
import com.example.dogbreeds.ui.component.CoilImage
import com.example.dogbreeds.ui.component.InitialLoadLoading
import com.example.dogbreeds.util.SnackBarHost
import com.example.dogbreeds.util.showErrorMessage

@Composable
fun BreedImagesScreen(
    viewModel: BreedImagesViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current
    val favBreeds = viewModel.favBreeds.collectAsStateWithLifecycle()
    val imagesState = viewModel.fetchBreedImagesState.collectAsStateWithLifecycle()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.message.collect {
            it?.let { messageResId ->
                showErrorMessage(
                    context = context,
                    messageResId = messageResId,
                    scaffoldState = scaffoldState,
                    scope = this
                )
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        snackbarHost = { SnackBarHost(scaffoldState.snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.breeds_images_screen_top_bar_title)) },
                navigationIcon = { AppBarBackButton(onBackClick = onBackClick) }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (imagesState.value is FetchBreedImagesState.Loading) {
                InitialLoadLoading(modifier = Modifier.align(Alignment.Center))
            } else if (imagesState.value is FetchBreedImagesState.BreedImagesLoaded) {
                val images =
                    (imagesState.value as FetchBreedImagesState.BreedImagesLoaded).breedImages
                ImagesList(
                    images = images,
                    favBreeds = favBreeds.value,
                    onLikeChanged = viewModel::updateFavorites
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagesList(
    images: List<String>,
    favBreeds: List<FavBreed>,
    onLikeChanged: (Int, String, Boolean) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier.fillMaxSize(),
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(images) { imageUrl ->
            ImageItem(imageUrl = imageUrl, favBreeds = favBreeds, onLikeChanged = onLikeChanged)
        }
    }
}

@Composable
fun ImageItem(
    imageUrl: String,
    favBreeds: List<FavBreed>,
    onLikeChanged: (Int, String, Boolean) -> Unit,
) {
    val id = imageUrl.hashCode()
    val isLiked: Boolean by remember(favBreeds) {
        mutableStateOf(
            favBreeds.any { it.imageId == id }
        )
    }
    Box(
        modifier = Modifier
            .pointerInput(favBreeds) {
                detectTapGestures(
                    onDoubleTap = {
                        onLikeChanged(id, imageUrl, !isLiked)
                    }
                )
            }
    ) {
        CoilImage(
            imageUrl,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(4.dp))
        )
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            visible = isLiked,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            LikeButton()
        }
    }
}

@Composable
fun LikeButton() {
    Icon(
        painter = painterResource(id = R.drawable.ic_favorite_filled),
        contentDescription = "",
        tint = Color.Red,
    )
}