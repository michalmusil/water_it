package cz.mendelu.xmusil5.waterit.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var navigationView: NavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

        // SETTING UP THE NAVIGATION
        navController = findNavController(R.id.nav_host_fragment_content_main)
        drawerLayout = binding.drawerLayout

        navigationView = binding.navigationView
        bottomNavigation = findViewById(R.id.bottomNavigation)

        // only adding the top level fragments - these fragments reset the navigation nesting and display burger icon upon entering them
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.PlantsListFragment,
            R.id.RoomsListFragment,
            R.id.AlertsFragment
        ), drawerLayout)

        bottomNavigation.setupWithNavController(navController)
        navigationView.setupWithNavController(navController)

        setupActionBarWithNavController(navController, appBarConfiguration)


        // When an item in Navigation view or bottom navigation is selected, backstack is popped
        bottomNavigation.setOnItemSelectedListener {
            NavigationUI.onNavDestinationSelected(it, navController)
            navController.popBackStack(it.itemId, false)
        }
        navigationView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawers()
            NavigationUI.onNavDestinationSelected(it, navController)
            navController.popBackStack(it.itemId, false)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


}

