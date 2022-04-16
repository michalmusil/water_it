package cz.mendelu.xmusil5.waterit.database.entities

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

    @ColumnInfo(name = "description")
    var description: String? = null

}
