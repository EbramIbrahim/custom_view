package com.example.customview

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.customview.ui.theme.CustomViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomViewTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = viewModel<DrawingViewModel>()
                    val state = viewModel.drawingState.collectAsStateWithLifecycle()
                    val context = LocalContext.current
                    val bitmap = remember {
                        BitmapFactory.decodeResource(
                            context.resources,
                            R.drawable.cr_7
                        )
                    }
                    val imageBitmap = remember(bitmap) {
                        bitmap?.asImageBitmap()
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        if (imageBitmap != null) {
                            DrawingCanvas(
                                paths = state.value.paths,
                                currentPath = state.value.currentPath,
                                onAction = viewModel::onAction,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                imageBitmap = imageBitmap
                            )
                        }

                        CanvasControllerItem(
                            selectedColor = state.value.selectedColor,
                            allColors = allColors,
                            onSelectedColor = {
                                viewModel.onAction(DrawingAction.OnSelectColor(it))
                            },
                            onCanvasCleared = {
                                viewModel.onAction(DrawingAction.OnClearCanvasClick)
                            }
                        )
                    }
                }
            }
        }
    }
}






