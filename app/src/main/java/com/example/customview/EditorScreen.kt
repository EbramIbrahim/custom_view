package com.example.customview

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.core.net.toUri
import com.example.customview.edit_screen.CanvasControllerItem
import com.example.customview.edit_screen.DrawingAction
import com.example.customview.edit_screen.DrawingCanvas
import com.example.customview.edit_screen.DrawingViewModel
import com.example.customview.edit_screen.allColors
import com.example.customview.utils.BitmapUtil

@Composable
fun EditorScreen(croppedImage: String) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val viewModel = viewModel<DrawingViewModel>()
        val state = viewModel.drawingState.collectAsStateWithLifecycle()
        val context = LocalContext.current
        val bitmap = remember {
            val originalUri: Uri = croppedImage.toUri()
            BitmapUtil.loadBitmapFromUri(originalUri, context)
        }
        val imageBitmap = remember(bitmap) {
            bitmap.asImageBitmap()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DrawingCanvas(
                paths = state.value.paths,
                currentPath = state.value.currentPath,
                onAction = viewModel::onAction,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                imageBitmap = imageBitmap,
                rotation = state.value.rotation
            )

            CanvasControllerItem(
                selectedColor = state.value.selectedColor,
                allColors = allColors,
                onSelectedColor = {
                    viewModel.onAction(DrawingAction.OnSelectColor(it))
                },
                onCanvasCleared = {
                    viewModel.onAction(DrawingAction.OnClearCanvasClick)
                },
                onImageRotated = {
                    viewModel.onAction(DrawingAction.OnRotationChanged(rotation = (state.value.rotation + 90f) % 360f))
                }
            )
        }
    }
}