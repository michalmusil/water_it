package cz.mendelu.xmusil5.waterit.di

import cz.mendelu.xmusil5.waterit.ui.plants.addplant.AddPlantViewModel
import cz.mendelu.xmusil5.waterit.ui.plants.plantdetail.PlantDetailViewModel
import cz.mendelu.xmusil5.waterit.ui.plants.plantsList.PlantsViewModel
import cz.mendelu.xmusil5.waterit.ui.rooms.addroom.AddRoomViewModel
import cz.mendelu.xmusil5.waterit.ui.rooms.roomdetail.RoomDetailViewModel
import cz.mendelu.xmusil5.waterit.ui.rooms.roomslist.RoomsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    // Plant ViewModels
    viewModel{
        PlantsViewModel(get())
    }
    viewModel {
        PlantDetailViewModel(get())
    }
    viewModel {
        AddPlantViewModel(get())
    }


    // Room ViewModels
    viewModel {
        RoomsListViewModel(get())
    }
    viewModel {
        RoomDetailViewModel(get())
    }
    viewModel {
        AddRoomViewModel(get())
    }
}