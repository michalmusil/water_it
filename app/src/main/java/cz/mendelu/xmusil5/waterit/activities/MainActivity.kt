package cz.mendelu.xmusil5.waterit.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.alerts.AlertManager
import cz.mendelu.xmusil5.waterit.databinding.ActivityMainBinding
import cz.mendelu.xmusil5.waterit.utils.NotificationUtils
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var navigationView: NavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var notificationChannel: NotificationChannel
    private val alertManager: AlertManager by inject()


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
            R.id.HomeFragment,
            R.id.PlantsListFragment,
            R.id.RoomsListFragment,
            R.id.AlertsFragment,
            R.id.SettingsFragment,
            R.id.AboutFragment
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
    }

    override fun onPause() {
        lifecycleScope.launch {
            alertManager.scheduleNotifications()
        }
        super.onPause()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(){
        notificationChannel = NotificationChannel(NotificationUtils.channelId, NotificationUtils.channelName, NotificationUtils.channelImportance)
        notificationChannel.description = NotificationUtils.channelDescription
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }


}

