package cz.mendelu.xmusil5.waterit.ui.plants.plantdetail

import androidx.lifecycle.ViewModel
import cz.mendelu.xmusil5.waterit.database.entities.relations.PlantWithRoom
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository
import java.lang.IllegalStateException

class PlantDetailViewModel(private val repository: IPlantsLocalRepository): ViewModel() {

    var plantId: Long = -1L
    lateinit var plantWithRoom: PlantWithRoom

    suspend fun fetchPlant(){
        if (plantId >= 0) {
            plantWithRoom = repository.getPlantWithRoom(plantId)
        } else{
            throw IllegalStateException()
        }
    }

    suspend fun deletePlant(){
        if (plantId >= 0) {
            repository.delete(plantWithRoom.plant)
        } else{
            throw IllegalStateException()
        }
    }
}