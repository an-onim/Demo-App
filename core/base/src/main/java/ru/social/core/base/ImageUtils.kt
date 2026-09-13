package ru.social.core.base

import android.content.Context
import android.net.Uri
import android.util.Base64

fun Uri.toBase64(context: Context): String {
    val contentResolver = context.contentResolver
    return contentResolver.openInputStream(this)?.readBytes()?.let { bytes ->
        Base64.encodeToString(bytes, Base64.DEFAULT)
    } ?: ""
}

object ImageUtils {

    fun base64ToUri(image: String): Uri? {
        return try {
            Uri.parse(image)
        } catch (e: Exception) {
            null
        }
    }

}
