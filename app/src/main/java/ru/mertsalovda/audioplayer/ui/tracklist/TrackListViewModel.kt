package ru.mertsalovda.audioplayer.ui.tracklist

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import ru.mertsalovda.audioplayer.App
import ru.mertsalovda.audioplayer.ui.model.Track

class TrackListViewModel : ViewModel() {

    private lateinit var disposable: Disposable

    private val _isLoad = MutableLiveData(false)
    val isLoad: MutableLiveData<Boolean> = _isLoad

    private val _tracks = MutableLiveData<List<Track>>(mutableListOf())
    val tracks: MutableLiveData<List<Track>> = _tracks

    @SuppressLint("CheckResult")
    fun load(){
//        isLoad.postValue(true)
//
//        disposable = Observable.fromCallable {
//            SearchSoundsUtils.getFiles(
//                Environment.getRootDirectory(),
//                mutableListOf()
//            )
//        }
//            .subscribe {
//                val result = mutableListOf<Track>()
//                for (file in it) {
//                    result.add(Track(file.title, 0, file.absolutePath))
//                }
//                tracks.postValue(result)
//                isLoad.postValue(false)
//            }
        tracks.postValue(App.getRepository().getAll())
    }

    override fun onCleared() {
        super.onCleared()
        if(!disposable.isDisposed){
            disposable.dispose()
        }
    }
}