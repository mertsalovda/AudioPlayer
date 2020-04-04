package ru.mertsalovda.audioplayer.ui.player

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fr_player.*

import ru.mertsalovda.audioplayer.R
import ru.mertsalovda.audioplayer.ui.model.Track

class PlayerFragment() : Fragment() {
    private lateinit var track: Track

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            track = it.getSerializable(ARG_TRACK) as Track
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_player, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tvTrackName.text = track.name
    }

    companion object {

        const val ARG_TRACK = "ARG_TRACK"

        @JvmStatic
        fun newInstance(track: Track) =
            PlayerFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_TRACK, track)
                }
            }
    }
}
