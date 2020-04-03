package ru.mertsalovda.audioplayer.ui.tracklist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ru.mertsalovda.audioplayer.R

class TrackListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_track_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = TrackListFragment()
    }
}
