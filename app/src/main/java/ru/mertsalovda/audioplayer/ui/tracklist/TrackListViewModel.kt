package ru.mertsalovda.audioplayer.ui.tracklist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mertsalovda.audioplayer.ui.model.Track

class TrackListViewModel : ViewModel() {

    private val _isLoad = MutableLiveData<Boolean>(false)
    val isLoad: MutableLiveData<Boolean> = _isLoad

    private val _tracks = MutableLiveData<List<Track>>(mutableListOf())
    val tracks: MutableLiveData<List<Track>> = _tracks

    fun load(){
        isLoad.postValue(true)
        val list = listOf(Track("Track", 123465, ""))
        tracks.postValue(list)
        isLoad.postValue(false)
    }

}