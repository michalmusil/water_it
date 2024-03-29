package cz.mendelu.xmusil5.waterit.ui.plants.plantsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository
import cz.mendelu.xmusil5.waterit.rwadapters.PlantsRecyclerViewAdapter

class PlantsListViewModel(private val plantsRepository: IPlantsLocalRepository): ViewModel() {

    val plantsList: MutableList<DbPlant> = mutableListOf()
    lateinit var layoutManager: LinearLayoutManager
    lateinit var plantsAdapter: PlantsRecyclerViewAdapter

    fun getAll(): LiveData<MutableList<DbPlant>>{
        return plantsRepository.getAll()
    }

    fun getAllWithRoomId(roomId: Long): LiveData<MutableList<DbPlant>>{
        return plantsRepository.getAllWithRoomId(roomId)
    }
}