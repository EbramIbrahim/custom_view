package com.example.customview.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.customview.crop_screen.CropperScreen
import com.example.customview.EditorScreen


@Composable
fun SetupNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.EditorScreen
    ) {
        composable<Screen.CropperScreen> {
            CropperScreen(navController = navController)
        }
        composable<Screen.EditorScreen> {  backStackEntry ->
//            val editorScreen = backStackEntry.toRoute<Screen.EditorScreen>()
            EditorScreen()
        }

    }

}









