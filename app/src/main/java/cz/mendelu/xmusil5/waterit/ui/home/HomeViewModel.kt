package cz.mendelu.xmusil5.waterit.ui.home

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.mendelu.xmusil5.waterit.alerts.AlertManager
import cz.mendelu.xmusil5.waterit.alerts.models.AlertModel
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository
import cz.mendelu.xmusil5.waterit.rwadapters.AlertsRecyclerViewAdapter
import cz.mendelu.xmusil5.waterit.rwadapters.PlantsRecyclerViewAdapter
import cz.mendelu.xmusil5.waterit.ui.alerts.AlertsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel (private val plantsRepository: IPlantsLocalRepository,
                     private val alertManager: AlertManager): ViewModel() {

    var alerts: MutableList<AlertModel> = mutableListOf()
    lateinit var alertsLayoutManager: LinearLayoutManager
    lateinit var alertsAdapter: AlertsRecyclerViewAdapter

    var plantsList: MutableList<DbPlant> = mutableListOf()
    lateinit var plantsLayoutManager: RecyclerView.LayoutManager
    lateinit var plantsAdapter: PlantsRecyclerViewAdapter

    suspend fun loadAlerts(){
        alerts = alertManager.getAllAlerts()
    }

    fun updateCheckedAlerts(){
        CoroutineScope(Dispatchers.IO).launch {
            alertManager.propagateChecked(alerts)
        }
    }

    suspend fun loadMostRecentlyPlanted(){
        val plants = plantsRepository.getAllStatic()
        val plantsFiltered = plants.filter {
            it.dateOfPlanting != null
        }.sortedByDescending { it.dateOfPlanting }.take(5)
        plantsList = plantsFiltered.toMutableList()
    }

}