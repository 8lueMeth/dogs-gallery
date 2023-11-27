package com.example.dogbreeds.ui.screens.breeds

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dogbreeds.R
import com.example.dogbreeds.model.Breed
import com.example.dogbreeds.model.ParentBreed
import com.example.dogbreeds.model.SubBreed
import com.example.dogbreeds.ui.component.CoilImage
import com.example.dogbreeds.ui.component.InitialLoadLoading
import com.example.dogbreeds.util.SnackBarHost
import com.example.dogbreeds.util.showErrorMessage
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun BreedsScreen(
    viewModel: BreedsViewModel = hiltViewModel(),
    navigateToBreedImages: (Breed) -> Unit,
    navigateToFavoriteImages: () -> Unit,
) {
    val context = LocalContext.current
    val breedsState = viewModel.fetchBreedsState.collectAsStateWithLifecycle()
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
                title = { Text(text = stringResource(id = R.string.breeds_screen_top_bar_title)) },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (breedsState.value is FetchBreedsState.Loading) {
                InitialLoadLoading(modifier = Modifier.align(Alignment.Center))
            } else if (breedsState.value is FetchBreedsState.BreedsLoaded) {
                val breeds = (breedsState.value as FetchBreedsState.BreedsLoaded).breeds
                BreedsList(
                    breeds = breeds,
                    fetchImageUrl = viewModel::getRandomImageByBreed,
                    onBreedItemClick = { breed -> navigateToBreedImages(breed) }
                )
            }
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp),
                onClick = { navigateToFavoriteImages() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_favorite_outline),
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = stringResource(id = R.string.navigate_to_favorites_content_description)
                )
            }
        }
    }
}

@Composable
fun BreedsList(
    breeds: List<ParentBreed>,
    fetchImageUrl: (Breed) -> SharedFlow<String?>,
    onBreedItemClick: (Breed) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(breeds.flatMap { listOf(it) + it.subBreeds }) { breedItem ->
            BreedItem(
                breedItem = breedItem,
                fetchImageUrl = fetchImageUrl,
                onBreedItemClick = onBreedItemClick
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BreedItem(
    breedItem: Breed,
    fetchImageUrl: (Breed) -> SharedFlow<String?>,
    onBreedItemClick: (Breed) -> Unit,
) {
    var imageUrl: String? by remember { mutableStateOf(null) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = breedItem) {
        scope.launch {
            imageUrl = fetchImageUrl(breedItem).first()
        }
    }
    Card(
        modifier = when (breedItem) {
            is ParentBreed -> Modifier.padding(0.dp)
            is SubBreed -> Modifier.padding(start = 24.dp)
        }
            .fillMaxWidth(),
        onClick = { onBreedItemClick(breedItem) },
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Card(
                elevation = 0.dp,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(16.dp)),
            ) {
                CoilImage(imageUrl)
            }
            Text(modifier = Modifier.padding(horizontal = 12.dp), text = breedItem.name)
        }
    }
}