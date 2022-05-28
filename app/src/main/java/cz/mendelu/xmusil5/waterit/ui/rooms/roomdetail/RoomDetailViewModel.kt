package cz.mendelu.xmusil5.waterit.ui.rooms.roomdetail

import androidx.lifecycle.ViewModel
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository
import cz.mendelu.xmusil5.waterit.database.repositories.rooms.IRoomsLocalRepository
import java.lang.IllegalStateException

class RoomDetailViewModel(private val roomsRepository: IRoomsLocalRepository,
                        private val plantsRepository: IPlantsLocalRepository): ViewModel() {

    var roomId: Long = -1L
    lateinit var room: DbRoom

    suspend fun fetchRoom(){
        if (roomId >= 0) {
            room = roomsRepository.findById(roomId)
        } else{
            throw IllegalStateException()
        }
    }

    suspend fun deleteRoom(){
        if (roomId >= 0) {
            roomsRepository.delete(room)
            plantsRepository.removeReferenceToRoom(roomId)
        } else{
            throw IllegalStateException()
        }
    }
}