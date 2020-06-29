package com.example.confinout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.confinout.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
            Initialising home framgment without backstacking so that to
            avoid empty screen when back button press on the first fragment.
         */
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.fragment_container, MapFragment.newInstance())
        fragmentTransaction.commit()

        // manging bottom menu selection
        setupNavigationElements()
    }

    /**
     * Displays the fragment passed as parameter by replacing it by the last one
     *
     * @param fragment : the fragment to display
     */
    fun replaceFragment(fragment: Fragment?) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragment?.let { fragmentTransaction.replace(R.id.fragment_container, it) }
        fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
    }


    /**
     * Accourding to the navigation button selected, this method sets the appropriate fragment.
     */
    fun setupNavigationElements() {

        val  navigationButton : BottomNavigationView = findViewById(R.id.bottom_navigation)

        navigationButton.setOnNavigationItemSelectedListener(object: BottomNavigationView.OnNavigationItemSelectedListener{

            override fun onNavigationItemSelected(itemMenu: MenuItem): Boolean {
                when (itemMenu.itemId) {
                    R.id.map -> {
                        val fragment : Fragment = MapFragment.newInstance()
                        replaceFragment(fragment)
                        return true
                    }
                    R.id.events -> {
                        val fragment : Fragment = EventsFragment.newInstance()
                        replaceFragment(fragment)
                        return true
                    }
                    R.id.favorite -> {
                        val fragment : Fragment = FavoriteFragment.newInstance()
                        replaceFragment(fragment)
                        return true
                    }
                    R.id.notifs -> {
                        val fragment : Fragment = NotifFragment.newInstance()
                        replaceFragment(fragment)
                        return true
                    }
                    else -> return false

                }
            }
        })

    }
}
