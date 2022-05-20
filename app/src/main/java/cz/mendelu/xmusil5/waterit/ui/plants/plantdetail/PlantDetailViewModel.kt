package cz.mendelu.xmusil5.waterit.ui.plants.plantdetail

import androidx.lifecycle.ViewModel
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository
import java.lang.IllegalStateException

class PlantDetailViewModel(private val repository: IPlantsLocalRepository): ViewModel() {

    var plantId: Long = -1L
    lateinit var plant: DbPlant

    suspend fun fetchPlant(){
        if (plantId >= 0) {
            plant = repository.findById(plantId)
        } else{
            throw IllegalStateException()
        }
    }
}