package cz.mendelu.xmusil5.waterit.ui.rooms.roomdetail

import androidx.lifecycle.ViewModel
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom
import cz.mendelu.xmusil5.waterit.database.repositories.rooms.IRoomsLocalRepository

class RoomDetailViewModel(private val repository: IRoomsLocalRepository): ViewModel() {
    suspend fun findById(id: Long): DbRoom{
        return repository.findById(id)
    }
}