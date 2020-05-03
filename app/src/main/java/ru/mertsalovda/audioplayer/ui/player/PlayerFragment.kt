package ru.mertsalovda.audioplayer.ui.player

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fr_player.*

import ru.mertsalovda.audioplayer.R
import ru.mertsalovda.audioplayer.ui.model.Track
import ru.mertsalovda.audioplayer.utils.TimeUtils

class PlayerFragment : Fragment() {
    private lateinit var track: Track
    private lateinit var viewModel: PlayerViewModel

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
        viewModel = ViewModelProviders.of(this).get(PlayerViewModel::class.java)
        viewModel.track = track
        viewModel.initMediaPlayer()
        return inflater.inflate(R.layout.fr_player, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tvTrackName.text = track.name

        btnPlay.setOnClickListener {
            viewModel.play()
        }

        btnPause.setOnClickListener {
            viewModel.pause()
        }

        btnStop.setOnClickListener {
            viewModel.stop()
        }

        btnForward.setOnClickListener {
            viewModel.forward(FORWARD)
        }

        btnRewind.setOnClickListener {
            viewModel.rewind(REWIND)
        }

        viewModel.maxProgress.observe(viewLifecycleOwner, Observer {
            progressBar.max = it
        })

        viewModel.progress.observe(viewLifecycleOwner, Observer {
            progressBar.progress = it
            tvProgress.text = TimeUtils.msecToMin(progressBar.max - it)
        })


        viewModel.status.observe(viewLifecycleOwner, Observer {
            tvStatus.text = it.name
        })
    }

    companion object {
        const val ARG_TRACK = "ARG_TRACK"
        const val FORWARD = 1000
        const val REWIND = 1000
    }
}
