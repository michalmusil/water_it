package cz.mendelu.xmusil5.waterit.ui.addplant

import androidx.lifecycle.ViewModel
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository

class AddPlantViewModel(private val repository: IPlantsLocalRepository): ViewModel() {
    suspend fun addPlant(plant: DbPlant){
        repository.insert(plant)
    }
}