package cz.mendelu.xmusil5.waterit.utils

import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi

object NotificationUtils {
    const val notificationIdExtraKey = "idExtraKey"
    const val notificationTitleExtraKey = "titleExtraKey"
    const val notificationMessageExtraKey = "messageExtraKey"

    const val channelId = "wateritNotificationChannel"
    const val channelName = "Plant watering notifications"
    const val channelDescription = "This channel is for notifications regarding watering the plants."
    @RequiresApi(Build.VERSION_CODES.N)
    const val channelImportance = NotificationManager.IMPORTANCE_HIGH

    const val notificationWaterPlantsMinuteDelay = 10
    const val notifiationWaterPlantsRequestCode = 9180532


}