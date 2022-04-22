package cz.mendelu.xmusil5.waterit.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rooms")
data class DbRoom(
    @ColumnInfo(name = "name")
    var name: String){

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

    @ColumnInfo(name = "picture")
    var picture: ByteArray? = null

    @ColumnInfo(name = "latitude")
    var latitude: Double? = null

    @ColumnInfo(name = "longitude")
    var longitude: Double? = null

    @ColumnInfo(name = "description")
    var description: String? = null
}
