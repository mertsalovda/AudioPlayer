package ru.mertsalovda.audioplayer.ui.tracklist

import android.annotation.SuppressLint
import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import ru.mertsalovda.audioplayer.ui.model.Track
import ru.mertsalovda.audioplayer.utils.SearchSoundsUtils
import java.io.File

class TrackListViewModel : ViewModel() {

    private lateinit var disposable: Disposable

    private val _isLoad = MutableLiveData(false)
    val isLoad: MutableLiveData<Boolean> = _isLoad

    private val _tracks = MutableLiveData<List<Track>>(mutableListOf())
    val tracks: MutableLiveData<List<Track>> = _tracks

    @SuppressLint("CheckResult")
    fun load(){
        isLoad.postValue(true)
        val list = listOf(Track("Track", 123465, ""))
        tracks.postValue(list)

        Observable.fromCallable { SearchSoundsUtils.getFiles(Environment.getRootDirectory(), mutableListOf()) }
            .subscribe {
                val result = mutableListOf<Track>()
                for (file in it) {
                    result.add(Track(file.name, 0, file.absolutePath))
                }
                tracks.postValue(result)
                isLoad.postValue(false)
            }
    }

}