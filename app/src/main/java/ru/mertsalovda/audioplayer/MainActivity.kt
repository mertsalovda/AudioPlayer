package ru.mertsalovda.audioplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNavigationView = findViewById(R.id.bottomNavigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.pageMusic -> {
                    navController.navigate(R.id.action_global_trackListFragment)
                    true
                }
                R.id.pagePlaying -> {
                    navController.navigate(R.id.action_global_playerFragment)
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
