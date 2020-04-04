package ru.mertsalovda.audioplayer.ui.tracklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.mertsalovda.audioplayer.R
import ru.mertsalovda.audioplayer.ui.model.Track
import ru.mertsalovda.audioplayer.utils.TrackDiffUtilsCallback

class TrackListAdapter(private val listener: TrackListAdapter.onClickListener) : RecyclerView.Adapter<TrackHolder>() {

    private val tracks = mutableListOf<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.li_track, parent, false)
        return TrackHolder(view)
    }

    override fun getItemCount(): Int = tracks.size

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        holder.bind(tracks[position], listener)
    }

    fun addData(list: List<Track>?, clear: Boolean) {
        if (list == null) {
            return
        }
        val messagesDiffUtils = TrackDiffUtilsCallback(tracks, list)
        val diffUtils = DiffUtil.calculateDiff(messagesDiffUtils)
        if (clear) {
            tracks.clear()
        }
        tracks.addAll(list)
        diffUtils.dispatchUpdatesTo(this)
    }

    interface onClickListener{
        fun onClick(track: Track)
    }
}