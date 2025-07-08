package com.example.customview.edit_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.customview.R

@Composable
fun DrawingSection(modifier: Modifier = Modifier) {
    val viewModel = viewModel<DrawingViewModel>()
    val state = viewModel.drawingState.collectAsStateWithLifecycle()
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.cr_7),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        DrawingCanvas(
            paths = state.value.paths,
            currentPath = state.value.currentPath,
            onAction = viewModel::onAction,
            modifier = Modifier.fillMaxSize(),
            drawingMode = state.value.drawingMode
        )

    }
}