package com.example.customview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.customview.crop_rotate_screen.presentation.ui.ImageEditorScreen
import com.example.customview.crop_rotate_screen.presentation.viewmodel.EditImageViewModel
import com.example.customview.ui.theme.CustomViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomViewTheme {
                val viewModel by viewModels<EditImageViewModel>()
                val degree = viewModel.rotationDegree.collectAsStateWithLifecycle()
                ImageEditorScreen(viewModel = viewModel, degree = degree.value)
            }
        }
    }
}






