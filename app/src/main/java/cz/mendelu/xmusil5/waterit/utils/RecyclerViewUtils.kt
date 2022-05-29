package cz.mendelu.xmusil5.waterit.utils

import android.util.DisplayMetrics
import cz.mendelu.xmusil5.waterit.rwadapters.AlertsRecyclerViewAdapter

class RecyclerViewUtils {

    companion object{
        val ALERTS_COLUMN_MIN_WIDTH_DP = 160
        val ROOMS_COLUMN_MIN_WIDTH_DP = 120

        fun columnCountAlerts(displayMetrics: DisplayMetrics): Int{
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            return (dpWidth / ALERTS_COLUMN_MIN_WIDTH_DP).toInt()
        }

        fun columnCountRooms(displayMetrics: DisplayMetrics): Int{
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            return (dpWidth / ROOMS_COLUMN_MIN_WIDTH_DP).toInt()
        }
    }
}