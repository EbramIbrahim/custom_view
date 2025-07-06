package com.example.customview.crop_rotate_screen.presentation.ui


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.customview.crop_rotate_screen.presentation.component.EditableImage
import com.example.customview.crop_rotate_screen.presentation.viewmodel.EditImageViewModel
import com.example.customview.ui.theme.CustomViewTheme
import com.example.customview.utils.BitmapUtil
import kotlinx.coroutines.launch
import java.io.FileDescriptor


@Composable
fun ImageEditorScreen(
    viewModel: EditImageViewModel,
    degree: Float
) {
    CustomViewTheme {
        val context = LocalContext.current

        val scope = rememberCoroutineScope()

        val pickVisualMediaLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { contentUri ->
            contentUri?.let { uri ->
                context.contentResolver.openFileDescriptor(uri, "r").use {
                    it?.let { parcelFileDescriptor ->
                        val fd: FileDescriptor = parcelFileDescriptor.fileDescriptor
                        val bitmap: Bitmap = BitmapFactory.decodeFileDescriptor(fd)
                        viewModel.setInputBitmap(bitmap)
                    }
                }
            }
        }

        val permissionRequestLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                pickVisualMediaLauncher.launch(
                    PickVisualMediaRequest(
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xff614bc3)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            viewModel.inputBitmap.value?.let {
                EditableImage(
                    bitmap = it.asImageBitmap(),
                    onCrop = viewModel::onCrop
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
            viewModel.editedBitmap.value?.let {
                Image(bitmap = it.asImageBitmap(), contentDescription = null)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    viewModel.updateDegree((degree + 90f) % 360f)
                    viewModel.rotate()
                }) {
                    Text("Rotate")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff33bbc5)
                    ),
                    onClick = {
                        scope.launch {
                            val fileName = "output_${System.currentTimeMillis()}.jpg"
                            val savedUri = BitmapUtil.saveImageToExternalStorage(
                                context = context,
                                savableBitmap = it,
                                outputFileName = fileName
                            )
                            savedUri?.let {
                                Toast.makeText(
                                    context,
                                    "Cropped Image saved successfully",
                                    Toast.LENGTH_LONG
                                ).show()
                            } ?: Toast.makeText(
                                context,
                                "Can't save image file",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                ) {
                    Text(text = "Save Edited Image")
                }
            }
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff33bbc5)
                ),
                onClick = {
                    if (readPermissionsGranted(context)) {
                        pickVisualMediaLauncher.launch(
                            PickVisualMediaRequest(
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            permissionRequestLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        } else {
                            permissionRequestLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    }
                }
            ) {
                Text(text = "Pick Image")
            }
        }

    }
}

private fun readPermissionsGranted(context: Context): Boolean {
    val readPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    return ContextCompat.checkSelfPermission(
        context,
        readPermission
    ) == PackageManager.PERMISSION_GRANTED
}
