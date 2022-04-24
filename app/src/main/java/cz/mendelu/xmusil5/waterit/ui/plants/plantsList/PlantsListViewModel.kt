package cz.mendelu.xmusil5.waterit.ui.plants.plantsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository

class PlantsListViewModel(private val repository: IPlantsLocalRepository): ViewModel() {

    fun getAll(): LiveData<MutableList<DbPlant>>{
        return repository.getAll()
    }
}