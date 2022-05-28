package cz.mendelu.xmusil5.waterit.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.entities.relations.PlantWithRoom

@Dao
interface PlantsDao {

    @Query("SELECT * FROM plants ORDER BY plants.name")
    fun getAll(): LiveData<MutableList<DbPlant>>

    @Query("SELECT * FROM plants ORDER BY plants.name")
    suspend fun getAllStatic(): MutableList<DbPlant>

    @Query("SELECT * FROM plants WHERE roomId = :roomId ORDER BY plants.name")
    fun getAllWithRoomId(roomId: Long): LiveData<MutableList<DbPlant>>

    @Query("SELECT * FROM plants WHERE id = :plantId")
    suspend fun getPlantWithRoom(plantId: Long): PlantWithRoom

    @Query("SELECT * FROM plants WHERE id = :id")
    suspend fun findById(id: Long): DbPlant

    @Query("UPDATE plants SET roomId = null WHERE roomId = :roomId")
    suspend fun removeReferenceToRoom(roomId: Long)

    @Insert
    suspend fun insert(plant: DbPlant): Long

    @Update
    suspend fun update(plant: DbPlant)

    @Delete
    suspend fun delete(plant: DbPlant)
}