package ru.mertsalovda.audioplayer.ui.tracklist

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.mertsalovda.audioplayer.R
import ru.mertsalovda.audioplayer.ui.model.Track

class TrackHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val name = itemView.findViewById<TextView>(R.id.tvName)

    fun bind(
        track: Track,
        listener: TrackListAdapter.onClickListener
    ) {
        name.text = track.title
        itemView.setOnClickListener{
            listener.onClick(track)
        }
    }

}
