package cz.mendelu.xmusil5.waterit.notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.utils.NotificationUtils

class NotificationReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notification = NotificationCompat.Builder(context, NotificationUtils.channelId)
            .setSmallIcon(R.drawable.ic_waterit_notification)
            .setContentTitle(intent.getStringExtra(NotificationUtils.notificationTitleExtraKey))
            .setContentText(intent.getStringExtra(NotificationUtils.notificationMessageExtraKey))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        val notificationId = intent.getIntExtra(NotificationUtils.notificationIdExtraKey, 0)
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationId, notification)
    }
}