package ru.mertsalovda.audioplayer.ui.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fr_player.*
import ru.mertsalovda.audioplayer.R
import ru.mertsalovda.audioplayer.ui.model.Track

class PlayerFragment : Fragment() {
    private lateinit var track: Track
    private lateinit var viewModel: PlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val argument = it.getSerializable(ARG_TRACK) ?: Track()
            track = argument as Track
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
        tvTrackName.text = track.title

        btnPlayPause.setOnClickListener {
            viewModel.nextStatus()
        }

        viewModel.status.observe(viewLifecycleOwner, Observer {
            if (it == Status.PAUSE) {
                btnPlayPause.setImageResource(R.drawable.ic_pause_black_64dp)
            } else {
                btnPlayPause.setImageResource(R.drawable.ic_play_arrow_black_64dp)
            }
        })

        btnForward.setOnClickListener {
            viewModel.forward(FORWARD)
        }

        btnRewind.setOnClickListener {
            viewModel.rewind(REWIND)
        }

        viewModel.maxProgress.observe(viewLifecycleOwner, Observer {
            progressBar.max = it
        })

//        viewModel.progress.observe(viewLifecycleOwner, Observer {
//            progressBar.progress = it
//            tvProgress.text = TimeUtils.msecToMin(progressBar.max - it)
//        })

    }

    override fun onPause() {
        super.onPause()
//        viewModel.stop()
    }

    companion object {
        const val ARG_TRACK = "ARG_TRACK"
        const val FORWARD = 1000
        const val REWIND = 1000
    }
}
