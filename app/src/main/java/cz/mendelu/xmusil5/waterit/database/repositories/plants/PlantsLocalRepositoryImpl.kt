package cz.mendelu.xmusil5.waterit.database.repositories.plants

import androidx.lifecycle.LiveData
import cz.mendelu.xmusil5.waterit.database.daos.PlantsDao
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant

class PlantsLocalRepositoryImpl(private val plantsDao: PlantsDao): IPlantsLocalRepository {
    override fun getAll(): LiveData<MutableList<DbPlant>> {
        return plantsDao.getAll()
    }

    override suspend fun findById(id: Long): DbPlant {
        return plantsDao.findById(id)
    }

    override suspend fun insert(plant: DbPlant): Long {
        return plantsDao.insert(plant)
    }

    override suspend fun update(plant: DbPlant) {
        plantsDao.update(plant)
    }

    override suspend fun delete(plant: DbPlant) {
        plantsDao.delete(plant)
    }
}