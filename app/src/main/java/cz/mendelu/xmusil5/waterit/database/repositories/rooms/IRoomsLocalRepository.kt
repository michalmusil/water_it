package cz.mendelu.xmusil5.waterit.database.repositories.rooms

import androidx.lifecycle.LiveData
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom

interface IRoomsLocalRepository {
    fun getAll(): LiveData<MutableList<DbRoom>>

    suspend fun findById(id: Long): DbRoom

    suspend fun insert(room: DbRoom): Long

    suspend fun update(room: DbRoom)

    suspend fun delete(room: DbRoom)
}