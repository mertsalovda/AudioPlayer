package ru.mertsalovda.audioplayer.ui.player

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fr_player.*
import ru.mertsalovda.audioplayer.R
import ru.mertsalovda.audioplayer.extensions.millisecToTime
import ru.mertsalovda.audioplayer.ui.model.Track

class PlayerFragment : Fragment() {
    private lateinit var track: Track
    private lateinit var viewModel: PlayerViewModel
    private var isNotTracking = true
    private var listener: ItemClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val argument = it.getSerializable(ARG_TRACK) ?: Track()
            track = argument as Track
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ItemClickListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(PlayerViewModel::class.java)
        viewModel.setTrackById(track.id)
        return inflater.inflate(R.layout.fr_player, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tvTrackName.text = track.title

        btnPlayPause.setOnClickListener {
            viewModel.nextStatus()
        }

        viewModel.status.observe(viewLifecycleOwner, Observer {
            if (it == Status.PAUSE) {
                btnPlayPause.setImageResource(R.drawable.ic_play_arrow_black_64dp)
            } else {
                btnPlayPause.setImageResource(R.drawable.ic_pause_black_64dp)
            }
        })

        viewModel.track.observe(viewLifecycleOwner, Observer {
            tvTrackAuthor.text = it.artist
            tvTrackName.text = it.title
        })

        btnForward.setOnClickListener {
            viewModel.seekTo(FORWARD)
        }

        btnRewind.setOnClickListener {
            viewModel.seekTo(REWIND)
        }

        btnSkipNext.setOnClickListener {
            viewModel.nextTrack()
        }

        btnSkipPrev.setOnClickListener {
            viewModel.prevTrack()
        }

        viewModel.maxProgress.observe(viewLifecycleOwner, Observer {
            progressBar.max = it
        })

        progressBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var tmpProgress = 0
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                tvMaxProgress.text = "-${(seekBar.max - progress).millisecToTime()}"
                tvProgress.text = progress.millisecToTime()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                isNotTracking = false
                tmpProgress = seekBar.progress
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                viewModel.seekTo(seekBar.progress - tmpProgress)
                isNotTracking = true
            }

        })

        viewModel.progress.observe(viewLifecycleOwner, Observer {
            if (isNotTracking) {
                progressBar.progress = it
            }
        })

    }

    override fun onPause() {
        super.onPause()
        viewModel.stop()
    }

    interface ItemClickListener {
        fun onItemClick()
    }

    companion object {
        const val TAG = "PLAYER_FRAGMENT_TAG"
        const val ARG_TRACK = "ARG_TRACK"
        const val FORWARD = 10000
        const val REWIND = -10000

        fun newInstance(track: Track): PlayerFragment {
            val args = Bundle()
            args.putSerializable(ARG_TRACK, track)
            val fragment = PlayerFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
