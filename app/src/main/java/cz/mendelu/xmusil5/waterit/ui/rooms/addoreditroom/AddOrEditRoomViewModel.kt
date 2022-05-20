package cz.mendelu.xmusil5.waterit.ui.rooms.addoreditroom

import androidx.lifecycle.ViewModel
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom
import cz.mendelu.xmusil5.waterit.database.repositories.rooms.IRoomsLocalRepository
import java.lang.IllegalStateException

class AddOrEditRoomViewModel(private val repository: IRoomsLocalRepository): ViewModel() {

    var roomId: Long = -1L
    var room: DbRoom = DbRoom("")

    suspend fun fetchRoom(){
        if (roomId >= 0){
            room = repository.findById(roomId)
        } else{
            throw IllegalStateException()
        }
    }

    suspend fun saveRoom(){
        if (roomId < 0){
            repository.insert(room)
        } else{
            repository.update(room)
        }
    }
}