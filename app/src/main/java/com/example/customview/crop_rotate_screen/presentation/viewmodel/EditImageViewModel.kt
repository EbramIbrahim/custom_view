package com.example.customview.crop_rotate_screen.presentation.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Rect
import androidx.lifecycle.ViewModel
import com.example.customview.crop_rotate_screen.services.ImageEditor
import com.example.customview.crop_rotate_screen.services.ImageEditorImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditImageViewModel: ViewModel() {


    private var _inputBitmap: MutableState<Bitmap?> = mutableStateOf(null)
    val inputBitmap
        get() = _inputBitmap

    private var _editedBitmap: MutableState<Bitmap?> = mutableStateOf(null)
    val editedBitmap
        get() = _editedBitmap

    private val _rotationDegree = MutableStateFlow(0f)
    val rotationDegree = _rotationDegree.asStateFlow()

    private val imageEditor: ImageEditor = ImageEditorImpl()

    fun onCrop(cropRect: Rect) {
        _editedBitmap.value = imageEditor.crop(inputBitmap.value!!, cropRect)
    }

    fun rotate() {
        _editedBitmap.value = imageEditor.rotate(editedBitmap.value!!, _rotationDegree.value)
    }

    fun updateDegree(degree: Float) {
        _rotationDegree.value = degree
    }

    fun setInputBitmap(bitmap: Bitmap?) {
        _inputBitmap.value = bitmap
    }

}