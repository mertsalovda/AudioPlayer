package ru.mertsalovda.audioplayer.ui.model

import java.io.Serializable

data class Track(val name: String, val duration: Long, val path: String) : Serializable
