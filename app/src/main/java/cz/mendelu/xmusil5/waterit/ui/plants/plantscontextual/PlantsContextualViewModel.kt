package cz.mendelu.xmusil5.waterit.ui.plants.plantscontextual

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository
import cz.mendelu.xmusil5.waterit.database.repositories.rooms.IRoomsLocalRepository
import cz.mendelu.xmusil5.waterit.rwadapters.PlantsRecyclerViewAdapter

class PlantsContextualViewModel(private val plantsRepository: IPlantsLocalRepository
, private val roomsRepository: IRoomsLocalRepository) : ViewModel() {

    var room: DbRoom? = null

    val plantsList: MutableList<DbPlant> = mutableListOf()
    lateinit var layoutManager: LinearLayoutManager
    lateinit var plantsAdapter: PlantsRecyclerViewAdapter

    fun getAllWithRoomId(roomId: Long): LiveData<MutableList<DbPlant>>{
        return plantsRepository.getAllWithRoomId(roomId)
    }

    suspend fun getRoomById(id: Long): DbRoom{
        return roomsRepository.findById(id)
    }
}