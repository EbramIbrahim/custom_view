package com.example.customview.crop_rotate_screen.services

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Rect

interface ImageEditor {
    fun crop(bitmap: Bitmap, cropRect: Rect): Bitmap
    fun rotate(bitmap: Bitmap, degree: Float): Bitmap
}