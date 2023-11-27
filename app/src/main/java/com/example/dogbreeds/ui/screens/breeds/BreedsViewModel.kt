package com.example.dogbreeds.ui.screens.breeds

import androidx.lifecycle.viewModelScope
import com.example.dogbreeds.data.BreedsRepository
import com.example.dogbreeds.extension.errorResId
import com.example.dogbreeds.model.Breed
import com.example.dogbreeds.model.ParentBreed
import com.example.dogbreeds.ui.screens.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class FetchBreedsState {
    object Idle : FetchBreedsState()
    object Loading : FetchBreedsState()
    object NotLoading : FetchBreedsState()
    data class BreedsLoaded(val breeds: List<ParentBreed>) : FetchBreedsState()
}

@HiltViewModel
class BreedsViewModel @Inject constructor(
    private val breedsRepository: BreedsRepository,
) : BaseViewModel() {

    private val _fetchBreedsState: MutableStateFlow<FetchBreedsState> =
        MutableStateFlow(FetchBreedsState.Idle)
    val fetchBreedsState: StateFlow<FetchBreedsState> = _fetchBreedsState

    private val randomImageFlows: MutableMap<Breed, String?> = HashMap()

    init {
        getAllBreeds()
    }

    fun getRandomImageByBreed(breed: Breed): SharedFlow<String?> = flow {
        (randomImageFlows[breed] ?: run {
            val result = breedsRepository.getRandomImageByBreed(breed).getOrNull()
            randomImageFlows[breed] = result
            result
        }).let { emit(it) }
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 1)

    private fun getAllBreeds() {
        _fetchBreedsState.value = FetchBreedsState.Loading

        viewModelScope.launch {
            val result = breedsRepository.getAllBreeds()

            if (result.isSuccess) {
                val breeds = result.getOrDefault(emptyList())
                _fetchBreedsState.value =
                    FetchBreedsState.BreedsLoaded(breeds = breeds)
            } else {
                _fetchBreedsState.value = FetchBreedsState.NotLoading
                setErrorMessage(result.errorResId)
            }
        }
    }
}