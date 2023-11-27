package com.example.dogbreeds.ui.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.dogbreeds.R

@Composable
fun AppBarBackButton(onBackClick: () -> Unit) {
    IconButton(onClick = { onBackClick() }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = stringResource(id = R.string.navigate_back_content_description)
        )
    }
}