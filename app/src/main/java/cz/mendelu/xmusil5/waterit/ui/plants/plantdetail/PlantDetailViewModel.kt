package cz.mendelu.xmusil5.waterit.ui.plants.plantdetail

import androidx.lifecycle.ViewModel
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository

class PlantDetailViewModel(private val repository: IPlantsLocalRepository): ViewModel() {

    suspend fun findById(id: Long): DbPlant{
        return repository.findById(id)
    }
}