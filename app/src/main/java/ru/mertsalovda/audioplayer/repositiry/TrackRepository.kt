package ru.mertsalovda.audioplayer.repositiry

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import ru.mertsalovda.audioplayer.ui.model.Track


class TrackRepository(private var tracks: MutableList<Track> = mutableListOf()) :
    IRepository<Track> {

    private var current = 0

    override fun getAll(): MutableList<Track> = tracks

    override fun getById(id: Long): Track = tracks.filter { it.id == id }[0]

    override fun getNext(): Track {
        return if (current + 1 != tracks.size) {
            current++
            tracks[current]
        } else {
            tracks[current]
        }
    }

    override fun getPrevious(): Track {
        return if (current - 1 < 0) {
            tracks[current]
        } else {
            current--
            tracks[current]
        }
    }

    override fun getCurrent(): Track = tracks[current]

    override fun insert(item: Track): Boolean = tracks.add(item)

    override fun insertAll(items: List<Track>): Boolean = tracks.addAll(items)

    override fun update(item: Track): Boolean {
        var track: Track? = tracks.filter { it.id == item.id }[0]
        return if (track != null) {
            track = item
            true
        } else {
            false
        }
    }

    override fun delete(item: Track): Boolean = tracks.remove(item)

    override fun deleteById(id: Long): Boolean {
        val track: Track? = tracks.filter { it.id == id }[0]
        return if (track != null) {
            tracks.remove(track)
        } else {
            false
        }
    }

    fun loadDataOnDeviceMemory(context: Context) {
        val musicResolver: ContentResolver = context.contentResolver
        val musicUri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val musicCursor: Cursor? = musicResolver.query(musicUri, null, null, null, null)
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            val titleColumn: Int = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val idColumn: Int = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val artistColumn: Int = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val albumId: Int = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
            val durationUnit = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
            val path = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            val albumKey: Int = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_KEY)
            //add songs to list
            do {
                val id: Long = musicCursor.getLong(idColumn)
                val title: String = musicCursor.getString(titleColumn)
                val artist: String = musicCursor.getString(artistColumn)
                val path: String = musicCursor.getString(path)
                val uri = ContentUris.withAppendedId(musicUri, id)
                insert(
                    Track(
                        id = id,
                        title = title,
                        artist = artist,
                        duration = durationUnit.toLong(),
                        path = path,
                        uri = uri
                    )
                )
            } while (musicCursor.moveToNext())
        }
    }
}