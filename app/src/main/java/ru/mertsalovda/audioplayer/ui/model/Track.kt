package ru.mertsalovda.audioplayer.ui.model

import android.net.Uri
import java.io.Serializable

data class Track(
    val id: Long = -1,
    val album: String = "",
    val artist: String = "",
    val title: String = "",
    val duration: Long = 0L,
    val path: String = "",
    val uri: Uri? = null,
    val imageUrl: String = ""
) : Serializable
