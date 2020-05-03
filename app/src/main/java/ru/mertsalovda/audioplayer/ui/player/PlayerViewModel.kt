package ru.mertsalovda.audioplayer.ui.player

import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mertsalovda.audioplayer.ui.model.Track

class PlayerViewModel : ViewModel() {

    lateinit var track: Track
    private var mediaPlayer: MediaPlayer? = null

    private val _status = MutableLiveData(Status.STOP)
    val status: MutableLiveData<Status> = _status

    private val _maxProgress = MutableLiveData(0)
    val maxProgress: MutableLiveData<Int> = _maxProgress

    private val _progress = MutableLiveData(0)
    val progress: MutableLiveData<Int> = _progress

    fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
        mediaPlayer?.apply {
            isLooping = true
            setDataSource(track.path)
            prepare()
        }
        maxProgress.postValue(mediaPlayer?.duration)
        status.postValue(Status.READY)
    }

    private fun start() {
        mediaPlayer?.start()
        status.postValue(Status.PLAY)
    }

    fun play() {
        when (status.value) {
            Status.READY -> start()
            Status.STOP -> {
                mediaPlayer?.prepare()
                start()
            }
            Status.PAUSE -> start()
            else -> return
        }
    }

    fun pause() {
        if (status.value == Status.PLAY) {
            mediaPlayer?.pause()
            status.postValue(Status.PAUSE)
        }
    }

    fun stop() {
        if (status.value == Status.PLAY || status.value == Status.PAUSE) {
            mediaPlayer?.stop()
            status.postValue(Status.STOP)
        }
    }

    fun forward(seconds: Int) {
        if (status.value == Status.PLAY || status.value == Status.PAUSE) {
        }
    }

    fun rewind(seconds: Int) {
        if (status.value == Status.PLAY || status.value == Status.PAUSE) {
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}

enum class Status {
    STOP,
    PAUSE,
    PLAY,
    READY
}