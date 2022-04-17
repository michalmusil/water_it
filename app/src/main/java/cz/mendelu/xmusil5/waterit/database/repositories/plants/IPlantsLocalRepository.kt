package cz.mendelu.xmusil5.waterit.database.repositories.plants

import androidx.lifecycle.LiveData
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant

interface IPlantsLocalRepository {
    fun getAll(): LiveData<MutableList<DbPlant>>

    suspend fun findById(id: Long): DbPlant

    suspend fun insert(plant: DbPlant): Long

    suspend fun update(plant: DbPlant)

    suspend fun delete(plant: DbPlant)
}