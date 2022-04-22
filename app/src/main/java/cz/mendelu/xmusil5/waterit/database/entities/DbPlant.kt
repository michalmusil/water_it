package cz.mendelu.xmusil5.waterit.database.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
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
    var picture: ByteArray? = null

    @ColumnInfo(name = "dateOfPlanting")
    var dateOfPlanting: Long? = null

    @ColumnInfo(name = "lastWatered")
    var lastWatered: Long? = null

    @ColumnInfo(name = "daysBetweenWatering")
    var daysBetweenWatering: Int? = null

    @ColumnInfo(name = "description")
    var description: String? = null

}
