package com.example.customview.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

object BitmapUtil {

    fun loadBitmapFromUri(uri: Uri, context: Context): Bitmap {
        val options = BitmapFactory.Options().apply {
            inSampleSize = 2
        }
        val inputStream = context.contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(inputStream, null, options) ?: throw IllegalArgumentException(
            "Failed to load bitmap"
        )
    }
}