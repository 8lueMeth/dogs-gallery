package com.example.dogbreeds.ui.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

open class BaseViewModel : ViewModel() {

    private val _message = MutableSharedFlow<Int?>()
    val message: SharedFlow<Int?> = _message

    suspend fun setErrorMessage(resId: Int) {
        _message.emit(resId)
    }
}