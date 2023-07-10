package com.projectAnya.stunthink.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
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

    return compressImageFile(outputFile)
}


fun createImageFile(context: Context, displayName: String): File {
    val directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File(directory, displayName)
}

fun compressImageFile(imageFile: File): File {
    val originalExif = ExifInterface(imageFile.absolutePath)
    val orientation = originalExif.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_UNDEFINED
    )

    val options = BitmapFactory.Options()

    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(imageFile.absolutePath, options)

    val maxWidth = 1080
    val maxHeight = 1920

    options.inSampleSize = calculateSampleSize(options, maxWidth, maxHeight)

    options.inJustDecodeBounds = false
    val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath, options)

    val rotatedBitmap = rotateBitmap(bitmap, orientation)

    val compressedFile = createCompressedFile(imageFile)

    val outputStream = FileOutputStream(compressedFile)
    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)

    outputStream.close()
    bitmap.recycle()
    rotatedBitmap.recycle()

    return compressedFile
}

private fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap {
    val matrix = Matrix()
    when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
        else -> return bitmap
    }
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

private fun calculateSampleSize(options: BitmapFactory.Options, maxWidth: Int, maxHeight: Int): Int {
    val width = options.outWidth
    val height = options.outHeight
    var sampleSize = 1

    if (width > maxWidth || height > maxHeight) {
        val widthRatio = Math.round(width.toFloat() / maxWidth.toFloat())
        val heightRatio = Math.round(height.toFloat() / maxHeight.toFloat())
        sampleSize = if (widthRatio < heightRatio) widthRatio else heightRatio
    }

    return sampleSize
}

private fun createCompressedFile(imageFile: File): File {
    val directory = imageFile.parentFile
    val compressedFileName = "compressed_${imageFile.name}"
    return File(directory, compressedFileName)
}