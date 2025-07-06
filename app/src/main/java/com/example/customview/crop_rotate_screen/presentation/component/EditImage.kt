package com.example.customview.crop_rotate_screen.presentation.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp

@Composable
fun EditableImage(
    bitmap: ImageBitmap,
    onCrop: (Rect) -> Unit
) {
    var cropMarkerTopLeft by remember { mutableStateOf(Offset.Unspecified) }
    var cropMarkerBottomRight by remember { mutableStateOf(Offset.Unspecified) }

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        EditableImageLayout {
            Image(bitmap = bitmap, contentDescription = null)
            ImageCropperView(
                onCropMarkerChanged = {
                    cropMarkerTopLeft = it.first
                    cropMarkerBottomRight = it.second
                }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                if(cropMarkerTopLeft == Offset.Unspecified || cropMarkerBottomRight == Offset.Unspecified) return@Button
                // Crop incoming bitmap with the following Rect.
                val cropRect = Rect(cropMarkerTopLeft, cropMarkerBottomRight)
                onCrop(cropRect)
            }
        ) {
            Text(text = "Crop Now")
        }
    }
}