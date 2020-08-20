package ru.mertsalovda.audioplayer.ui.player

import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mertsalovda.audioplayer.ui.model.Track
import java.util.*

class PlayerViewModel : ViewModel() {

    lateinit var track: Track
    private var mediaPlayer: MediaPlayer? = null

    private var timer: Timer? = null
    private var progressTask: ProgressTask? = null

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
            if (track.path.isNotEmpty()) {
                setDataSource(track.path)
            }
            prepare()
        }
        maxProgress.postValue(mediaPlayer?.duration)
        progress.postValue(0)
        status.postValue(Status.READY)
        start()
    }

    private fun start() {
        if (timer != null) {
            timer?.cancel()
        }
        timer = Timer("Progress")
        progressTask = ProgressTask()
        timer?.schedule(progressTask, 200, 200)
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

            if (timer != null) {
                timer?.cancel();
                timer = null;
            }
        }
    }

    fun forward(seconds: Int) {
        if (status.value == Status.PLAY || status.value == Status.PAUSE) {
            var current: Int? = 0
            if (mediaPlayer != null) {
                current = mediaPlayer?.currentPosition
            }
            current?.plus(seconds)?.let { mediaPlayer?.seekTo(it) }
        }
    }

    fun rewind(seconds: Int) {
        if (status.value == Status.PLAY || status.value == Status.PAUSE) {
            var current: Int? = 0
            if (mediaPlayer != null) {
                current = mediaPlayer?.currentPosition
            }
            current?.minus(seconds)?.let { mediaPlayer?.seekTo(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
        mediaPlayer = null
        if (timer != null) {
            timer?.cancel();
            timer = null;
        }
    }

    inner class ProgressTask : TimerTask() {
        override fun run() {
            progress.postValue(mediaPlayer?.currentPosition)
        }

    }

}

enum class Status {
    STOP,
    PAUSE,
    PLAY,
    READY,
}