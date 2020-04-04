package ru.mertsalovda.audioplayer.ui.tracklist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fr_track_list.*
import ru.mertsalovda.audioplayer.MainActivity

import ru.mertsalovda.audioplayer.R
import ru.mertsalovda.audioplayer.ui.model.Track
import ru.mertsalovda.audioplayer.ui.player.PlayerFragment

class TrackListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener,
    TrackListAdapter.onClickListener {

    private lateinit var viewModel: TrackListViewModel
    private val adapter = TrackListAdapter(this)
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(TrackListViewModel::class.java)
        return inflater.inflate(R.layout.fr_track_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler.layoutManager = LinearLayoutManager(this.context)
        recycler.adapter = adapter

        refresher.setOnRefreshListener(this)

        viewModel.isLoad.observe(viewLifecycleOwner, Observer {
            refresher.isRefreshing = it
        })

        viewModel.tracks.observe(viewLifecycleOwner, Observer {

            adapter.addData(it, true)
        })
        onRefresh()
    }

    override fun onRefresh() {
        viewModel.load()
    }

    companion object {
        @JvmStatic
        fun newInstance() = TrackListFragment()
    }

    override fun onClick(track: Track) {
        val bundle = Bundle()
        bundle.putSerializable(PlayerFragment.ARG_TRACK, track)
        navController.navigate(R.id.action_trackListFragment_to_playerFragment, bundle)
    }
}
