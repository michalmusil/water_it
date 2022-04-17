package cz.mendelu.xmusil5.waterit.database.repositories.rooms

import androidx.lifecycle.LiveData
import cz.mendelu.xmusil5.waterit.database.daos.RoomsDao
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom

class RoomsLocalRepositoryImpl(private val roomsDao: RoomsDao): IRoomsLocalRepository {
    override fun getAll(): LiveData<MutableList<DbRoom>> {
        return roomsDao.getAll()
    }

    override suspend fun findById(id: Long): DbRoom {
        return roomsDao.findById(id)
    }

    override suspend fun insert(room: DbRoom): Long {
        return roomsDao.insert(room)
    }

    override suspend fun update(room: DbRoom) {
        roomsDao.update(room)
    }

    override suspend fun delete(room: DbRoom) {
        roomsDao.delete(room)
    }
}