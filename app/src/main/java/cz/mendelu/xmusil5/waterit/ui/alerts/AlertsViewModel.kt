package cz.mendelu.xmusil5.waterit.ui.alerts

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import cz.mendelu.xmusil5.waterit.alerts.AlertManager
import cz.mendelu.xmusil5.waterit.alerts.models.AlertModel
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository

class AlertsViewModel(private val plantsRepository: IPlantsLocalRepository,
    private val alertManager: AlertManager): ViewModel() {

    var alerts: MutableList<AlertModel> = mutableListOf()
    lateinit var layoutManager: LinearLayoutManager
    lateinit var alertsAdapter: AlertsFragment.AlertsRecyclerViewAdapter

    suspend fun loadAlerts(){
        alerts = alertManager.getAllAlerts()
    }



}