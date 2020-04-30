package ru.mertsalovda.audioplayer.ui.player

import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mertsalovda.audioplayer.ui.model.Track

class PlayerViewModel : ViewModel() {

    lateinit var track: Track
    private var mediaPlayer = MediaPlayer()

    private val _status = MutableLiveData(Status.STOP)
    val status: MutableLiveData<Status> = _status

    private val _progress = MutableLiveData(0)
    val progress: MutableLiveData<Int> = _progress

    fun play() {
        if (!mediaPlayer.isPlaying) {
            status.postValue(Status.PLAY)
            mediaPlayer.apply {
                isLooping = true
                mediaPlayer.setDataSource(track.path)
                prepare()
                start()
            }
        }
    }

    fun pause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            status.postValue(Status.PAUSE)
        }
    }

    fun forward(seconds: Int) {
        if (mediaPlayer.isPlaying) {
        }
    }

    fun rewind(seconds: Int) {
        if (mediaPlayer.isPlaying) {
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
    }
}

enum class Status {
    STOP,
    PAUSE,
    PLAY
}