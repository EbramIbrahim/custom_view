package com.example.customview.navigation


import kotlinx.serialization.Serializable

interface Screen {


    @Serializable
    data object CropperScreen: Screen

    @Serializable
    data object EditorScreen: Screen
}