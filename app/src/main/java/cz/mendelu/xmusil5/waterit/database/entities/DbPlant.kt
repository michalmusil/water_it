package cz.mendelu.xmusil5.waterit.database.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import cz.mendelu.xmusil5.waterit.database.entities.converters.WateritTypeConverter
import java.util.*

@Entity(tableName = "plants")
@TypeConverters(WateritTypeConverter::class)
data class DbPlant(
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "species")
    var species: String) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

    @ColumnInfo(name = "roomId")
    var roomId: Long? = null



    @ColumnInfo(name = "picture")
    var picture: Bitmap? = null

    @ColumnInfo(name = "dateOfPlanting")
    var dateOfPlanting: Date? = null

    @ColumnInfo(name = "lastWatered")
    var lastWatered: Date? = null

    @ColumnInfo(name = "daysBetweenWatering")
    var daysBetweenWatering: Int? = null

    @ColumnInfo(name = "description")
    var description: String? = null

}
