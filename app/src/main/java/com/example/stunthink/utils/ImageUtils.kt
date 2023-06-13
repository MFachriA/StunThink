package com.example.stunthink.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.documentfile.provider.DocumentFile
import java.io.File
import java.io.FileOutputStream

fun getImageFileFromUri(uri: Uri, context: Context): File? {
    val documentFile = DocumentFile.fromSingleUri(context, uri)
    val displayName = documentFile?.name ?: return null

    val resolver = context.contentResolver
    val outputFile = createImageFile(context, displayName)

    resolver.openInputStream(uri)?.use { inputStream ->
        FileOutputStream(outputFile).use { outputStream ->
            inputStream.copyTo(outputStream)
        }
    }

    return outputFile
}

fun createImageFile(context: Context, displayName: String): File {
    val directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File(directory, displayName)
}