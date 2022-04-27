package cz.mendelu.xmusil5.waterit.ui.dialogfragments.rooms

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom
import cz.mendelu.xmusil5.waterit.database.repositories.rooms.IRoomsLocalRepository

class RoomsDialogFragmentViewModel(private val repository: IRoomsLocalRepository): ViewModel() {

    fun getAllRooms(): LiveData<MutableList<DbRoom>>{
        return repository.getAll()
    }
}