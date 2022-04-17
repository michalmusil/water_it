package cz.mendelu.xmusil5.waterit.database.entities.converters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.util.*


class WateritTypeConverter {

    @TypeConverter
    fun fromBitmapToByteArray(bitmap: Bitmap?): ByteArray?{
        if(bitmap != null) {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            return outputStream.toByteArray()
        } else{
            return null
        }
    }

    @TypeConverter
    fun fromByteArrayToBitmap(byteArray: ByteArray?): Bitmap?{
        if(byteArray != null) {
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            return bitmap
        } else{
            return null
        }
    }

    @TypeConverter
    fun fromDateToLong(date: Date?): Long?{
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromLongToDate(longDate: Long?): Date?{
        return longDate?.let { Date(longDate) }
    }
}