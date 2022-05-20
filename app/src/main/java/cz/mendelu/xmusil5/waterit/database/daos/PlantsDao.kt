package cz.mendelu.xmusil5.waterit.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.entities.relations.PlantWithRoom

@Dao
interface PlantsDao {

    @Query("SELECT * FROM plants")
    fun getAll(): LiveData<MutableList<DbPlant>>

    @Query("SELECT * FROM plants WHERE roomId = :roomId")
    fun getAllWithRoomId(roomId: Long): LiveData<MutableList<DbPlant>>

    @Query("SELECT * FROM plants WHERE id = :plantId")
    suspend fun getPlantWithRoom(plantId: Long): PlantWithRoom

    @Query("SELECT * FROM plants WHERE id = :id")
    suspend fun findById(id: Long): DbPlant

    @Insert
    suspend fun insert(plant: DbPlant): Long

    @Update
    suspend fun update(plant: DbPlant)

    @Delete
    suspend fun delete(plant: DbPlant)
}