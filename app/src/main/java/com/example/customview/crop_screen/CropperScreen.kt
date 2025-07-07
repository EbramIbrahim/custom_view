package com.example.customview.crop_screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.customview.navigation.Screen

@Composable
fun CropperScreen(navController: NavController) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (imageUri != null) {
            CropImageBox(
                uri = imageUri!!,
                onCropDone = {
                    val croppedPhoto = it.toString()
//                    navController.navigate(Screen.EditorScreen(croppedPhoto))
                },
                onNotAbleToCrop = {},
                onBackPressed = {}
            )
        }
        Button(onClick = { launcher.launch("image/*") }) {
            Text("Select Image")
        }
    }
}