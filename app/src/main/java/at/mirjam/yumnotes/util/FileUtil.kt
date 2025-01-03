package at.mirjam.yumnotes.util

import android.content.Context
import android.net.Uri
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

// Util class to save images to internal storage (used in Repositories)
object FileUtil {
    fun saveImageToInternalStorage(context: Context, uri: Uri): String {
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val outputStream: OutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)

        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return fileName
    }
}
