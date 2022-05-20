package cz.mendelu.xmusil5.waterit.ui.plants.addoreditplant

import androidx.lifecycle.ViewModel
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom
import cz.mendelu.xmusil5.waterit.database.entities.relations.PlantWithRoom
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository
import java.lang.IllegalStateException

class AddOrEditPlantViewModel(private val repository: IPlantsLocalRepository): ViewModel() {

    var plantId: Long = -1L
    var plantWithRoom: PlantWithRoom = PlantWithRoom(DbPlant("", ""), DbRoom(""))

    suspend fun fetchPlant(){
        if (plantId >= 0){
            plantWithRoom = repository.getPlantWithRoom(plantId!!)
        }else{
            throw IllegalStateException()
        }
    }

    suspend fun savePlant(){
        if (plantId < 0){
            repository.insert(plantWithRoom.plant)
        } else{
            repository.update(plantWithRoom.plant)
        }
    }
}