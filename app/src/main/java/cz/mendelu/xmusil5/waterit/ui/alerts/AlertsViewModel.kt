package cz.mendelu.xmusil5.waterit.ui.alerts

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import cz.mendelu.xmusil5.waterit.alerts.AlertManager
import cz.mendelu.xmusil5.waterit.alerts.models.AlertModel
import cz.mendelu.xmusil5.waterit.rwadapters.AlertsRecyclerViewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class AlertsViewModel(private val alertManager: AlertManager): ViewModel() {

    var alerts: MutableList<AlertModel> = mutableListOf()
    lateinit var layoutManager: LinearLayoutManager
    lateinit var alertsAdapter: AlertsRecyclerViewAdapter

    suspend fun loadAlerts(){
        alerts = alertManager.getAllAlerts()
    }

    fun updateCheckedAlerts(){
        CoroutineScope(IO).launch {
            alertManager.propagateChecked(alerts)
        }
    }



}