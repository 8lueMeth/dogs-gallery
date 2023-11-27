package com.example.dogbreeds.ui.screens.favoriteimages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogbreeds.data.FavBreedsRepository
import com.example.dogbreeds.model.FavBreed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteImagesViewModel @Inject constructor(
    private val favBreedsRepository: FavBreedsRepository,
) : ViewModel() {

    private val selectedFilter = MutableStateFlow<FavBreed?>(null)

    val breedFilters = favBreedsRepository.getAllFavBreeds()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val favBreeds = selectedFilter.flatMapLatest {
        if (it == null)
            favBreedsRepository.getAllFavBreeds()
        else
            favBreedsRepository.filterFavBreeds(it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())

    fun setSelectedFilter(favBreed: FavBreed?) {
        selectedFilter.value = favBreed
    }

    fun removeBreedFromFavorites(favBreed: FavBreed) {
        viewModelScope.launch {
            favBreedsRepository.updateFavorites(favBreed, false)
        }
    }
}