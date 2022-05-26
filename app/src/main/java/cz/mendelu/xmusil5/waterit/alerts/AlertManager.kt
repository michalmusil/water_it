package cz.mendelu.xmusil5.waterit.alerts

import cz.mendelu.xmusil5.waterit.alerts.models.AlertModel
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository
import cz.mendelu.xmusil5.waterit.utils.DateUtils
import java.time.temporal.ChronoUnit

class AlertManager(private val plantRepository: IPlantsLocalRepository) {

    suspend fun getAllAlerts(): MutableList<AlertModel>{
        var plants = plantRepository.getAllStatic()
        val alerts = plants.filter {
            if (it.lastWatered != null && it.daysBetweenWatering != null){
                val date = DateUtils.getDate(it.lastWatered!!)
                DateUtils.daysBetween(date, DateUtils.getCurrentDate()) > it.daysBetweenWatering!!
            } else {
                false
            }
        }.map {
            AlertModel(it)
        }
        return alerts.toMutableList()
    }

    suspend fun propagateChecked(alerts: MutableList<AlertModel>){
        alerts.forEach {
            if (it.checked){
                it.plant.lastWatered = DateUtils.getCurrentUnixTime()
                plantRepository.update(it.plant)
            }
        }
    }
}