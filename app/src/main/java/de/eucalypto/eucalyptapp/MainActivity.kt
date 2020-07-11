package de.eucalypto.eucalyptapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import de.eucalypto.eucalyptapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
        navController.addOnDestinationChangedListener { navController_: NavController, nd: NavDestination, args: Bundle? ->
            when (nd.id) {
                // Activate drawer in some destinations
                R.id.nav_about_me,
                navController_.graph.startDestination -> binding.drawerLayout.setDrawerLockMode(
                    DrawerLayout.LOCK_MODE_UNLOCKED
                )
                // Deactivate it on all other destinations
                else -> binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }
}
