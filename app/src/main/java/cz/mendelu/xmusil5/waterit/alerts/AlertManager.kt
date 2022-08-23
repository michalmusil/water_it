package cz.mendelu.xmusil5.waterit.alerts

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.alerts.models.AlertModel
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository
import cz.mendelu.xmusil5.waterit.notifications.NotificationReceiver
import cz.mendelu.xmusil5.waterit.utils.DateUtils
import cz.mendelu.xmusil5.waterit.utils.NotificationUtils
import java.lang.Exception
import java.time.format.DateTimeFormatter
import java.util.*

class AlertManager(private val plantRepository: IPlantsLocalRepository, private val context: Context) {

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
        scheduleNotifications()
    }

    suspend fun scheduleNotifications(){
        try {
            cancelPendingWateringNotifications()
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val plants = plantRepository.getAllStatic()
            val notificationTime = getWateringNotificationTiming(plants)
            if (notificationTime != null){

                val timeInString = notificationTime.time.toString()
                print(timeInString)


                val intent = createWateringNotificationIntent()
                val pendingIntent = PendingIntent.getBroadcast(
                    context, NotificationUtils.notifiationWaterPlantsRequestCode, intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    notificationTime.timeInMillis, pendingIntent)
            }
        } catch (exception: Exception){
            Log.e("Notification", "Could not schedule a notification for plant watering")
        }
    }

    private fun createWateringNotificationIntent(): Intent{
        val intent = Intent(context, NotificationReceiver::class.java)

        intent.putExtra(NotificationUtils.notificationIdExtraKey, DateUtils.getCurrentUnixTime())
        intent.putExtra(NotificationUtils.notificationTitleExtraKey, context.getString(R.string.notificationWateringTitle))
        intent.putExtra(NotificationUtils.notificationMessageExtraKey, context.getString(R.string.notificationWateringContent))

        return intent
    }

    private fun cancelPendingWateringNotifications(){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context,
            NotificationUtils.notifiationWaterPlantsRequestCode, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.cancel(pendingIntent)
    }

    private fun getWateringNotificationTiming(plants: List<DbPlant>): Calendar?{
        var mostPressingTime: Calendar? = null
        for (plant in plants){
            if (plant.lastWatered != null && plant.daysBetweenWatering != null){
                var timeToWater = DateUtils.getDate(plant.lastWatered!!)
                timeToWater.add(Calendar.DAY_OF_WEEK, plant.daysBetweenWatering!!)
                if (mostPressingTime == null){
                    mostPressingTime = timeToWater
                } else if (timeToWater < mostPressingTime){
                    mostPressingTime = timeToWater
                }
            }
        }
        if (mostPressingTime != null && DateUtils.daysBetween(DateUtils.getCurrentDate(), mostPressingTime) <= 0){
            var notificationTime = DateUtils.getCurrentDate()
            notificationTime.add(Calendar.HOUR, 1)
            return notificationTime
        }
        if (mostPressingTime != null){
            mostPressingTime.set(Calendar.HOUR, 9)
            mostPressingTime.set(Calendar.MINUTE, 0)
            mostPressingTime.set(Calendar.SECOND, 0)
        }
        return mostPressingTime
    }

}