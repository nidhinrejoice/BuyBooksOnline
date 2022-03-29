package com.nidhin.customerapp.presentation.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SnackBarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    snackbar: @Composable (SnackbarData) -> Unit = { Snackbar(it) }
) {
}