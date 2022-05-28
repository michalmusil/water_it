package cz.mendelu.xmusil5.waterit.database.repositories.plants

import androidx.lifecycle.LiveData
import androidx.room.Query
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.entities.relations.PlantWithRoom

interface IPlantsLocalRepository {
    fun getAll(): LiveData<MutableList<DbPlant>>

    suspend fun getAllStatic(): MutableList<DbPlant>

    fun getAllWithRoomId(roomId: Long): LiveData<MutableList<DbPlant>>

    suspend fun getPlantWithRoom(plantId: Long): PlantWithRoom

    suspend fun findById(id: Long): DbPlant

    suspend fun removeReferenceToRoom(roomId: Long)

    suspend fun insert(plant: DbPlant): Long

    suspend fun update(plant: DbPlant)

    suspend fun delete(plant: DbPlant)
}