package cz.mendelu.xmusil5.waterit.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom

data class RoomWithPlants(
    @Embedded
    val room: DbRoom,

    @Relation(
        parentColumn = "id",
        entityColumn = "roomId"
    )
    val plants: List<DbPlant>

)