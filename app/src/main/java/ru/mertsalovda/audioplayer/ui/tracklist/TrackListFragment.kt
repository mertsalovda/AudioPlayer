package ru.mertsalovda.audioplayer.ui.tracklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fr_track_list.*
import ru.mertsalovda.audioplayer.R
import ru.mertsalovda.audioplayer.ui.model.Track
import ru.mertsalovda.audioplayer.ui.player.PlayerFragment

class TrackListFragment : Fragment(), PlayerFragment.ItemClickListener,
    TrackListAdapter.onClickListener {

    private lateinit var viewModel: TrackListViewModel
    private val adapter = TrackListAdapter(this)
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(TrackListViewModel::class.java)
        return inflater.inflate(R.layout.fr_coordinator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler.layoutManager = LinearLayoutManager(this.context)
        recycler.adapter = adapter


        viewModel.tracks.observe(viewLifecycleOwner, Observer {

            adapter.addData(it, true)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    override fun onPause() {
        super.onPause()
    }

    companion object {
        private const val SHAKE_THRESHOLD = 2000
    }

    override fun onClick(track: Track) {
        val bundle = Bundle()
        bundle.putSerializable(PlayerFragment.ARG_TRACK, track)
        navController.navigate(R.id.action_trackListFragment_to_playerFragment, bundle)
    }

    override fun onItemClick() {
    }
}
