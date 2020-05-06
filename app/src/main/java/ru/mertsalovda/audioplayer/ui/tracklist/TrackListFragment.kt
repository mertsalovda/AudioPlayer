package ru.mertsalovda.audioplayer.ui.tracklist

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fr_track_list.*
import ru.mertsalovda.audioplayer.R
import ru.mertsalovda.audioplayer.ui.model.Track
import ru.mertsalovda.audioplayer.ui.player.PlayerFragment
import java.util.*
import kotlin.math.abs

class TrackListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener,
    TrackListAdapter.onClickListener {

    private lateinit var viewModel: TrackListViewModel
    private val adapter = TrackListAdapter(this)
    private lateinit var navController: NavController
    private lateinit var sensorManager: SensorManager
    private lateinit var sensorAcceleration: Sensor

    private var lastUpdate: Long = 0
    private var lastX: Float = 0.0f
    private var lastY: Float = 0.0f
    private var lastZ: Float = 0.0f

    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(sensorEvent: SensorEvent) {
            when (sensorEvent.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> {
                    val (x, y, z) = sensorEvent.values
                    val currentTime = System.currentTimeMillis()
                    val dt: Long = currentTime - lastUpdate
                    if (dt > 100) {
                        lastUpdate = currentTime
                        val speed = abs(x + y + z - lastX - lastY - lastZ) / dt * 10000
                        if (speed > SHAKE_THRESHOLD) {
                            val random = Random().nextInt(adapter.itemCount)
                            Toast.makeText(context, "Трек №$random", Toast.LENGTH_SHORT).show()
                            onClick(adapter.getTrack(random))
                        }
                        lastX = x;
                        lastY = y;
                        lastZ = z;
                    }
                }
            }

        }

        override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
    }

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

        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorAcceleration = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

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

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            sensorListener,
            sensorAcceleration,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorListener)
    }

    override fun onRefresh() {
        viewModel.load()
    }

    companion object {
        private const val SHAKE_THRESHOLD = 2000
    }

    override fun onClick(track: Track) {
        val bundle = Bundle()
        bundle.putSerializable(PlayerFragment.ARG_TRACK, track)
        navController.navigate(R.id.action_trackListFragment_to_playerFragment, bundle)
    }
}
