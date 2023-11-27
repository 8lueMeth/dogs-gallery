package com.example.dogbreeds.util

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun showErrorMessage(
    context: Context,
    messageResId: Int,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
) {
    scope.launch {
        scaffoldState.snackbarHostState.showSnackbar(context.getString(messageResId))
    }
}

@Composable
fun SnackBarHost(
    hostState: SnackbarHostState,
) {
    SnackbarHost(hostState) { data ->
        Snackbar(
            snackbarData = data,
            backgroundColor = Color.Gray,
            contentColor = Color.White,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }
}