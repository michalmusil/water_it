package cz.mendelu.xmusil5.waterit.database.entities.converters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.util.*


class WateritTypeConverter {

    @TypeConverter
    fun fromBitmapToByteArray(bitmap: Bitmap): ByteArray{
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun fromByteArrayToBitmap(byteArray: ByteArray): Bitmap{
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        return bitmap
    }

    @TypeConverter
    fun fromDateToLong(date: Date): Long{
        return date.time.toLong()
    }

    @TypeConverter
    fun fromLongToDate(longDate: Long): Date{
        return Date(longDate)
    }
}