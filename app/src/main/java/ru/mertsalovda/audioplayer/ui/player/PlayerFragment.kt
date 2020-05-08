package ru.mertsalovda.audioplayer.ui.player

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fr_player.*
import ru.mertsalovda.audioplayer.R
import ru.mertsalovda.audioplayer.ui.model.Track
import ru.mertsalovda.audioplayer.utils.TimeUtils

class PlayerFragment : Fragment() {
    private lateinit var track: Track
    private lateinit var viewModel: PlayerViewModel


    private lateinit var sensorManager: SensorManager
    private lateinit var sensorAcceleration: Sensor
    private val accelerometerReading = FloatArray(3)
    private val magnetometerReading = FloatArray(3)
    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)

    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                System.arraycopy(
                    event.values,
                    0,
                    accelerometerReading,
                    0,
                    accelerometerReading.size
                )
            } else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                System.arraycopy(event.values, 0, magnetometerReading, 0, magnetometerReading.size)
            }
            updateOrientationAngles()
        }

        override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
    }

    fun updateOrientationAngles() {
        SensorManager.getRotationMatrix(
            rotationMatrix,
            null,
            accelerometerReading,
            magnetometerReading
        )
        SensorManager.getOrientation(rotationMatrix, orientationAngles)
        Log.d(
            "TAG",
            "Z ${orientationAngles[0]}, X ${orientationAngles[1]}, Y ${orientationAngles[2]}"
        )
        if (orientationAngles[2] > 2.5f || orientationAngles[2] < -2.5f) {
            viewModel.pauseIfScreenDown()
        } else {
            viewModel.playIfScreenUp()
        }
    }

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

        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorAcceleration = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

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

    override fun onResume() {
        super.onResume()
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(
                sensorListener,
                accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_UI
            )
        }
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.also { magneticField ->
            sensorManager.registerListener(
                sensorListener,
                magneticField,
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.stop()
        sensorManager.unregisterListener(sensorListener)
    }

    companion object {
        const val ARG_TRACK = "ARG_TRACK"
        const val FORWARD = 1000
        const val REWIND = 1000
    }
}
