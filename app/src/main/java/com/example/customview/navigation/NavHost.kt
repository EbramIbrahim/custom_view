package com.example.customview.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.customview.EditorScreen


@Composable
fun SetupNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.CropperScreen
    ) {
        composable<Screen.CropperScreen> {

        }
        composable<Screen.EditorScreen> {  backStackEntry ->
            val editorScreen = backStackEntry.toRoute<Screen.EditorScreen>()
            EditorScreen(croppedImage = editorScreen.croppedPhoto)
        }

    }

}









