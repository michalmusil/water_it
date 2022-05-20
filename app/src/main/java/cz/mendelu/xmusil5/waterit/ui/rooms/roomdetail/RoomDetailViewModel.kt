package cz.mendelu.xmusil5.waterit.ui.rooms.roomdetail

import androidx.lifecycle.ViewModel
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom
import cz.mendelu.xmusil5.waterit.database.repositories.rooms.IRoomsLocalRepository
import java.lang.IllegalStateException

class RoomDetailViewModel(private val repository: IRoomsLocalRepository): ViewModel() {

    var roomId: Long = -1L
    lateinit var room: DbRoom

    suspend fun fetchRoom(){
        if (roomId >= 0) {
            room = repository.findById(roomId)
        } else{
            throw IllegalStateException()
        }
    }
}