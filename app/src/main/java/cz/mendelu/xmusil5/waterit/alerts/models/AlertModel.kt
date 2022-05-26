package cz.mendelu.xmusil5.waterit.alerts.models

import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.utils.DateUtils
import java.util.*

class AlertModel(
    val plant: DbPlant) {

    var checked = false

    val supposedWateringDateString: String
        get() {
            if (plant.lastWatered != null && plant.daysBetweenWatering != null) {
                val dateTime: Calendar = DateUtils.getDate(plant.lastWatered!!)
                dateTime.add(Calendar.DATE, plant.daysBetweenWatering!!)
                return DateUtils.getDateString(dateTime.timeInMillis)
            }
            return ""
        }
    val supposedWateringDayString: String
        get() {
            if (plant.lastWatered != null && plant.daysBetweenWatering != null) {
                val dateTime: Calendar = DateUtils.getDate(plant.lastWatered!!)
                return DateUtils.getDayOfTheWeekString(dateTime)
            }
            return ""
        }
}