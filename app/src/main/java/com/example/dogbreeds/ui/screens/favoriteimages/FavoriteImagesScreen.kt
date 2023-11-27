package com.example.dogbreeds.ui.screens.favoriteimages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dogbreeds.R
import com.example.dogbreeds.model.FavBreed
import com.example.dogbreeds.ui.component.AppBarBackButton
import com.example.dogbreeds.ui.component.CoilImage

@Composable
fun FavoriteImagesScreen(
    viewModel: FavoriteImagesViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val favBreeds = viewModel.favBreeds.collectAsStateWithLifecycle()
    val breedFilters = viewModel.breedFilters.collectAsStateWithLifecycle()
    val scaffoldState = rememberScaffoldState()

    var isFilterDialogVisible by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                title = { Text(text = stringResource(id = R.string.fav_breeds_screen_top_bar_title)) },
                actions = {
                    if (breedFilters.value.isNotEmpty()) IconButton(onClick = {
                        isFilterDialogVisible = true
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_filter_filter),
                            contentDescription = stringResource(id = R.string.filter_action_content_description)
                        )
                    }
                },
                navigationIcon = { AppBarBackButton(onBackClick = onBackClick) }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (favBreeds.value.isEmpty()) NoFavorites()
            else
                FavoriteBreedImages(
                    favBreeds = favBreeds.value,
                    removeBreedFromFavorites = viewModel::removeBreedFromFavorites
                )
            FilterDialog(
                breedFilters = breedFilters.value,
                isVisible = isFilterDialogVisible,
                onHideDialog = { isFilterDialogVisible = false },
                onApplyClick = { favBreed ->
                    viewModel.setSelectedFilter(favBreed)
                    isFilterDialogVisible = false
                },
                onClearClick = {
                    viewModel.setSelectedFilter(null)
                    isFilterDialogVisible = false
                },
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteBreedImages(
    favBreeds: List<FavBreed>,
    removeBreedFromFavorites: (FavBreed) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier.fillMaxSize(),
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(favBreeds) { favBreed ->
            FavoriteBreedImage(
                favBreed = favBreed,
                removeBreedFromFavorites = removeBreedFromFavorites
            )
        }
    }
}

@Composable
fun FavoriteBreedImage(favBreed: FavBreed, removeBreedFromFavorites: (FavBreed) -> Unit) {
    Box(modifier = Modifier
        .pointerInput(favBreed.imageId) {
            detectTapGestures(
                onDoubleTap = { removeBreedFromFavorites(favBreed) }
            )
        }) {
        CoilImage(
            favBreed.imageUrl,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(4.dp))
        )
        BreedTag(breedName = favBreed.subBreedName ?: favBreed.name)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxScope.BreedTag(breedName: String) {
    Chip(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .align(Alignment.TopStart),
        onClick = {}
    ) {
        Text(text = breedName, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
fun FilterDialog(
    isVisible: Boolean,
    breedFilters: List<FavBreed>,
    onHideDialog: () -> Unit,
    onApplyClick: (FavBreed) -> Unit,
    onClearClick: () -> Unit,
) {
    if (isVisible) {
        Dialog(
            onDismissRequest = {
                onHideDialog()
            },
            content = {
                FilterDialogContent(
                    favBreeds = breedFilters,
                    onApplyClick = onApplyClick,
                    onClearClick = onClearClick
                )
            }
        )
    }
}

@Composable
fun FilterDialogContent(
    favBreeds: List<FavBreed>,
    onApplyClick: (FavBreed) -> Unit,
    onClearClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = 8.dp
    ) {
        FilterRadioGroup(
            favBreeds = favBreeds,
            onApplyClick = onApplyClick,
            onClearClick = onClearClick
        )
    }
}

@Composable
fun FilterRadioGroup(
    favBreeds: List<FavBreed>,
    onApplyClick: (FavBreed) -> Unit,
    onClearClick: () -> Unit,
) {
    val breedFilters = favBreeds.distinctBy { it.subBreedName ?: it.name }

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(breedFilters[0]) }

    Column {
        LazyColumn {
            breedFilters.forEach { favBreed ->
                val text = favBreed.subBreedName ?: favBreed.name
                item {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(selected = (favBreed == selectedOption),
                                onClick = { onOptionSelected(favBreed) }
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (favBreed == selectedOption),
                            onClick = {
                                onOptionSelected(favBreed)
                            }
                        )
                        Text(
                            text = text,
                            modifier = Modifier.padding(start = 8.dp),
                        )
                    }
                }
            }
        }
        FilterDialogButtons(
            onClearClick = onClearClick,
            onApplyClick = { onApplyClick(selectedOption) },
            selectedFilter = selectedOption
        )
    }
}

@Composable
fun FilterDialogButtons(
    applyButtonColor: Color = MaterialTheme.colors.onSecondary,
    clearButtonColor: Color = MaterialTheme.colors.onBackground,
    onApplyClick: (FavBreed) -> Unit,
    onClearClick: () -> Unit,
    selectedFilter: FavBreed,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TextButton(
            onClick = onClearClick,
            colors = ButtonDefaults.textButtonColors(
                contentColor = clearButtonColor
            )
        ) {
            Text(
                text = stringResource(id = R.string.clear_filter_action),
                style = MaterialTheme.typography.button
            )
        }
        TextButton(
            onClick = { onApplyClick(selectedFilter) },
            colors = ButtonDefaults.textButtonColors(
                contentColor = applyButtonColor
            )
        ) {
            Text(
                text = stringResource(id = R.string.apply_filter_action),
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Composable
fun BoxScope.NoFavorites() {
    Column(
        modifier = Modifier.align(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_dog),
            contentDescription = stringResource(id = R.string.dog_icon_content_description),
            modifier = Modifier.alpha(0.5f)
        )
        Text(
            modifier = Modifier
                .alpha(0.5f)
                .padding(top = 8.dp),
            text = stringResource(id = R.string.no_favorites_text),
        )
    }
}