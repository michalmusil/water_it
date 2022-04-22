package cz.mendelu.xmusil5.waterit.ui.rooms.roomslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom
import cz.mendelu.xmusil5.waterit.database.repositories.rooms.IRoomsLocalRepository

class RoomsListViewModel(private var repository: IRoomsLocalRepository): ViewModel() {
    fun getAll(): LiveData<MutableList<DbRoom>>{
        return repository.getAll()
    }

    suspend fun insert(dbRoom: DbRoom): Long{
        return repository.insert(dbRoom)
    }
}