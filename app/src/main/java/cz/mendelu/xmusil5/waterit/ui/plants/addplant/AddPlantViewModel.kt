package cz.mendelu.xmusil5.waterit.ui.plants.addplant

import androidx.lifecycle.ViewModel
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository
import java.lang.IllegalStateException

class AddPlantViewModel(private val repository: IPlantsLocalRepository): ViewModel() {

    var plantId: Long? = null
    var plant: DbPlant = DbPlant("", "")

    suspend fun getPlant(){
        if (plantId != null){
            plant = repository.findById(plantId!!)
        }else{
            throw IllegalStateException()
        }
    }

    suspend fun savePlant(){
        if (plantId == null){
            repository.insert(plant)
        } else{
            repository.update(plant)
        }
    }
}