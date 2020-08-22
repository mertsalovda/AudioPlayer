package ru.mertsalovda.audioplayer

import android.app.Application
import android.content.Context
import android.util.Log
import ru.mertsalovda.audioplayer.repositiry.IRepository
import ru.mertsalovda.audioplayer.repositiry.TrackRepository
import ru.mertsalovda.audioplayer.ui.model.Track

class App : Application() {

    val appContext = this

    override fun onCreate() {
        super.onCreate()
        context = this
        repository.loadDataOnDeviceMemory(this)
        Log.d("TAG_Application", repository.getAll().toString())
    }

    companion object{
        private val repository: TrackRepository = TrackRepository()
        fun getRepository(): IRepository<Track> = repository
        var context: App? = null
        val appContext: Context?
            get() = context
    }
}