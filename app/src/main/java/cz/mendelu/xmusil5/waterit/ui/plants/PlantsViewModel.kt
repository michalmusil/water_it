package cz.mendelu.xmusil5.waterit.ui.plants

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository

class PlantsViewModel(private val repository: IPlantsLocalRepository): ViewModel() {

    fun getAll(): LiveData<MutableList<DbPlant>>{
        return repository.getAll()
    }

    suspend fun addPlant(plant: DbPlant){
        repository.insert(plant)
    }
}