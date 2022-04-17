package cz.mendelu.xmusil5.waterit.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom

@Dao
interface RoomsDao {

    @Query("SELECT * FROM rooms")
    fun getAll(): LiveData<MutableList<DbRoom>>

    @Query("SELECT * FROM rooms WHERE id = :id")
    suspend fun findById(id: Long): DbRoom

    @Insert
    suspend fun insert(room: DbRoom): Long

    @Update
    suspend fun update(room: DbRoom)

    @Delete
    suspend fun delete(room: DbRoom)
}