package cz.mendelu.xmusil5.waterit.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class PictureUtils {

    companion object{

        fun fromBitmapToByteArray(bitmap: Bitmap?): ByteArray?{
            if(bitmap != null) {
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                return outputStream.toByteArray()
            } else{
                return null
            }
        }

        fun fromByteArrayToBitmap(byteArray: ByteArray?): Bitmap?{
            if(byteArray != null) {
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                return bitmap
            } else{
                return null
            }
        }

    }
}