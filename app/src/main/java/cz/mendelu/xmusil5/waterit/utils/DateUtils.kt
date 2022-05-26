package cz.mendelu.xmusil5.waterit.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DateUtils {

    companion object {
        private val DATE_FORMAT_CS = "dd. MM. yyyy"
        private val DATE_FORMAT_EN = "yyyy/MM/dd"

        private val DAYS_OF_WEEK_CS: HashMap<Int, String>
            = hashMapOf(1 to "neděle", 2 to "pondělí", 3 to "úterý", 4 to "středa", 5 to "čtrvrek", 6 to "pátek", 7 to "sobota")
        private val DAYS_OF_WEEK_EN: HashMap<Int, String>
            = hashMapOf(1 to "Sunday", 2 to "Monday", 3 to "Tuesday", 4 to "Wednesday", 5 to "Thursday", 6 to "Friday", 7 to "Saturday")

        fun getDate(unixTime: Long): Calendar{
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = unixTime
            return calendar
        }

        fun getDateString(unixTime: Long): String{
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = unixTime
            val format: SimpleDateFormat;
            if (LanguageUtils.isLanguageCzech()){
                format = SimpleDateFormat(DATE_FORMAT_CS, Locale.GERMAN)
            } else {
                format = SimpleDateFormat(DATE_FORMAT_EN, Locale.ENGLISH)
            }
            return format.format(calendar.getTime())
        }

        fun getUnixTime(year: Int, month: Int, day: Int): Long {
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            return calendar.timeInMillis
        }

        fun getCurrentUnixTime(): Long{
            val calendar = Calendar.getInstance()
            return calendar.timeInMillis
        }

        fun getCurrentDate(): Calendar{
            return Calendar.getInstance()
        }

        fun getDayOfTheWeekString(dateTime: Calendar): String{
            val day = dateTime.get(Calendar.DAY_OF_WEEK)
            val map = if (LanguageUtils.isLanguageCzech()) DAYS_OF_WEEK_CS else DAYS_OF_WEEK_EN
            val dayString = map.get(day)
            return dayString!!
        }

        fun daysBetween(startDate: Calendar, endDate: Calendar): Long {
            val end = endDate.timeInMillis
            val start = startDate.timeInMillis
            return TimeUnit.MILLISECONDS.toDays(end - start)
        }
    }
}