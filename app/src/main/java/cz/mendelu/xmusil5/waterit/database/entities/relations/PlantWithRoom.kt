package cz.mendelu.xmusil5.waterit.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom

data class PlantWithRoom(
    @Embedded val plant: DbPlant,

    @Relation(
        parentColumn = "roomId",
        entityColumn = "id"
    ) var room: DbRoom? = null
)
