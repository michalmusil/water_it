package cz.mendelu.xmusil5.waterit.ui.rooms.addroom

import androidx.lifecycle.ViewModel
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom
import cz.mendelu.xmusil5.waterit.database.repositories.rooms.IRoomsLocalRepository

class AddRoomViewModel(private val repository: IRoomsLocalRepository): ViewModel() {
    suspend fun addRoom(room: DbRoom): Long{
        return repository.insert(room)
    }
}