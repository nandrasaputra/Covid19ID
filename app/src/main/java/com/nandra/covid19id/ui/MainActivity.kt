package com.nandra.covid19id.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.nandra.covid19id.R
import com.nandra.covid19id.utils.setVisibilityGone
import com.nandra.covid19id.utils.setVisibilityVisible
import com.nandra.covid19id.utils.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(R.navigation.home_nav,R.navigation.information_nav, R.navigation.support_nav)

        val controller = main_activity_bottom_navigation_view.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.main_activity_fragment_container,
            intent = intent
        )

        controller.observe(this) {
            addOnDestinationChangedListener(it)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        when(destination.id) {
            R.id.indonesiaProvinceDetailFragment, R.id.otherCountriesDetailFragment -> {showBottomNavBar(false) }
            else -> {showBottomNavBar(true)}
        }
    }

    private fun addOnDestinationChangedListener(navController: NavController) {
        navController.removeOnDestinationChangedListener(this)
        navController.addOnDestinationChangedListener(this)
    }

    private fun showBottomNavBar(show: Boolean) {
        if (show) {
            main_activity_bottom_navigation_view.setVisibilityVisible()
        } else {
            main_activity_bottom_navigation_view.setVisibilityGone()
        }
    }
}