package com.example.customview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.drawToBitmap
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.customview.edit_screen.CanvasControllerItem
import com.example.customview.edit_screen.DrawingAction
import com.example.customview.edit_screen.DrawingSection
import com.example.customview.edit_screen.DrawingViewModel
import com.example.customview.edit_screen.allColors
import com.example.customview.utils.BitmapUtil
import kotlinx.coroutines.launch

@Composable
fun EditorScreen() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val composeView = ComposeView(context).apply {
        setContent {
            DrawingSection()
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val viewModel = viewModel<DrawingViewModel>()
        val state = viewModel.drawingState.collectAsStateWithLifecycle()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AndroidView({ composeView }, modifier = Modifier
                .fillMaxSize()
                .weight(1f))

            CanvasControllerItem(
                selectedColor = state.value.selectedColor,
                allColors = allColors,
                onSelectedColor = {
                    viewModel.onAction(DrawingAction.OnSelectColor(it))
                },
                onCanvasCleared = {
                    viewModel.onAction(DrawingAction.OnClearCanvasClick)
                },
                onImageSaved = {
                    val bitMap = composeView.drawToBitmap()
                    scope.launch {
                        val fileName = "output_${System.currentTimeMillis()}.jpg"
                        BitmapUtil.saveImageToExternalStorage(
                            context = context,
                            savableBitmap = bitMap,
                            outputFileName = fileName
                        )
                    }
                },
                onChangeDrawingMode = { drawingMode ->
                    viewModel.onAction(DrawingAction.OnDrawModeChanged(drawingMode))
                }
            )
        }
    }
}