package com.example.customview.edit_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.customview.R

@Composable
fun DrawingSection() {
    val viewModel = viewModel<DrawingViewModel>()
    val state = viewModel.drawingState.collectAsStateWithLifecycle()
    val bitmap = ImageBitmap.imageResource(R.drawable.cr_7)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            bitmap,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        DrawingCanvas(
            paths = state.value.paths,
            currentPath = state.value.currentPath,
            onAction = viewModel::onAction,
            modifier = Modifier.fillMaxSize(),
        )

    }
}