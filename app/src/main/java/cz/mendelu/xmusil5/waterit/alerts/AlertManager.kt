package cz.mendelu.xmusil5.waterit.alerts

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.alerts.models.AlertModel
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository
import cz.mendelu.xmusil5.waterit.notifications.NotificationReceiver
import cz.mendelu.xmusil5.waterit.utils.DateUtils
import cz.mendelu.xmusil5.waterit.utils.NotificationUtils
import java.lang.Exception
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
            val wateringAlerts = getAllAlerts()
            if (wateringAlerts.size > 0){
                val intent = createWateringNotificationIntent(wateringAlerts.size)
                val pendingIntent = PendingIntent.getBroadcast(
                    context, NotificationUtils.notifiationWaterPlantsRequestCode, intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
                val pendingTime = getWateringNotificationTiming()
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, pendingTime.timeInMillis,
                    AlarmManager.INTERVAL_DAY,pendingIntent)
            }
        } catch (exception: Exception){
            Log.e("Notification", "Could not schedule a notification for plant watering")
        }
    }

    private fun createWateringNotificationIntent(numberOfPlantsToWater: Int): Intent{
        val intent = Intent(context, NotificationReceiver::class.java)

        intent.putExtra(NotificationUtils.notificationIdExtraKey, DateUtils.getCurrentUnixTime())
        intent.putExtra(NotificationUtils.notificationTitleExtraKey, context.getString(R.string.notificationWateringTitle))
        intent.putExtra(NotificationUtils.notificationMessageExtraKey,
            "${numberOfPlantsToWater} ${context.getString(R.string.notificationWateringContent)}")

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

    private fun getWateringNotificationTiming(): Calendar{
        val time = DateUtils.getCurrentDate()
        time.add(Calendar.SECOND, NotificationUtils.notificationWaterPlantsMinuteDelay)
        return time
    }
}