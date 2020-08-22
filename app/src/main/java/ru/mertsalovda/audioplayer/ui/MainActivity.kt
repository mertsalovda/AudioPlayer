package ru.mertsalovda.audioplayer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.mertsalovda.audioplayer.App
import ru.mertsalovda.audioplayer.R
import ru.mertsalovda.audioplayer.ui.player.PlayerFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)

        navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )
        bottomNavigationView = findViewById(R.id.bottomNavigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.pageMusic -> {
                    navController.navigate(R.id.action_global_trackListFragment)
                    true
                }
                R.id.pagePlaying -> {
                    val current = App.getRepository().getCurrent()
                    val bundle = Bundle()
                    bundle.putSerializable(PlayerFragment.ARG_TRACK, current)
                    navController.navigate(R.id.action_global_playerFragment, bundle)
                    true
                }
                R.id.pagePlaylists -> {
                    navController.navigate(R.id.action_global_playlistsFragment)
                    true
                }
                else -> false
            }
        }
    }
}
