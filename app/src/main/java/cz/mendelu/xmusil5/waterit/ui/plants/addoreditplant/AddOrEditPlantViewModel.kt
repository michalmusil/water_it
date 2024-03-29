package cz.mendelu.xmusil5.waterit.ui.plants.addoreditplant

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom
import cz.mendelu.xmusil5.waterit.database.entities.relations.PlantWithRoom
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository
import cz.mendelu.xmusil5.waterit.utils.DateUtils
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
            // inserting a new plant
            plantWithRoom.plant.lastWatered = DateUtils.getCurrentUnixTime()
            repository.insert(plantWithRoom.plant)
        } else{
            // updating an existing plant
            repository.update(plantWithRoom.plant)
        }
    }

}