package com.example.dogbreeds.ui.screens.breedimages

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.dogbreeds.data.BreedsRepository
import com.example.dogbreeds.data.FavBreedsRepository
import com.example.dogbreeds.model.Breed
import com.example.dogbreeds.model.FavBreed
import com.example.dogbreeds.model.ParentBreed
import com.example.dogbreeds.model.SubBreed
import com.example.dogbreeds.ui.screens.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class FetchBreedImagesState {
    object Idle : FetchBreedImagesState()
    object Loading : FetchBreedImagesState()
    object NotLoading : FetchBreedImagesState()
    data class BreedImagesLoaded(val breedImages: List<String>) : FetchBreedImagesState()
}

@HiltViewModel
class BreedImagesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val breedsRepository: BreedsRepository,
    private val favBreedsRepository: FavBreedsRepository
) : BaseViewModel() {

    private val navBreed = savedStateHandle.get<Breed>("breed")

    private val _fetchBreedImagesState: MutableStateFlow<FetchBreedImagesState> =
        MutableStateFlow(FetchBreedImagesState.Idle)
    val fetchBreedImagesState: StateFlow<FetchBreedImagesState> = _fetchBreedImagesState

    val favBreeds = favBreedsRepository.getAllFavBreeds()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        getAllBreedImages()
    }

    private fun getAllBreedImages() {
        navBreed ?: return

        _fetchBreedImagesState.value = FetchBreedImagesState.Loading
        viewModelScope.launch {
            val result = breedsRepository.getAllBreedImages(navBreed)

            if (result.isSuccess) {
                val breedImages = result.getOrDefault(emptyList())
                _fetchBreedImagesState.value =
                    FetchBreedImagesState.BreedImagesLoaded(breedImages = breedImages)
            } else {
                _fetchBreedImagesState.value = FetchBreedImagesState.NotLoading
            }
        }
    }

    fun updateFavorites(imageId: Int, imageUrl: String, isLiked: Boolean) {
        val favBreed = when (navBreed) {
            is ParentBreed -> FavBreed(navBreed.name, null, imageId, imageUrl)
            is SubBreed -> FavBreed(navBreed.parentName, navBreed.name, imageId, imageUrl)
            null -> return
        }

        viewModelScope.launch {
            favBreedsRepository.updateFavorites(favBreed, isLiked)
        }
    }
}