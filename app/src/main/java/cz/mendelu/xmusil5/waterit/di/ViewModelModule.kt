package cz.mendelu.xmusil5.waterit.di

import cz.mendelu.xmusil5.waterit.ui.alerts.AlertsViewModel
import cz.mendelu.xmusil5.waterit.ui.dialogfragments.rooms.RoomsDialogFragmentViewModel
import cz.mendelu.xmusil5.waterit.ui.plants.addoreditplant.AddOrEditPlantViewModel
import cz.mendelu.xmusil5.waterit.ui.plants.plantdetail.PlantDetailViewModel
import cz.mendelu.xmusil5.waterit.ui.plants.plantsList.PlantsListViewModel
import cz.mendelu.xmusil5.waterit.ui.plants.plantscontextual.PlantsContextualViewModel
import cz.mendelu.xmusil5.waterit.ui.rooms.addoreditroom.AddOrEditRoomViewModel
import cz.mendelu.xmusil5.waterit.ui.rooms.roomdetail.RoomDetailViewModel
import cz.mendelu.xmusil5.waterit.ui.rooms.roomslist.RoomsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    // Plant ViewModels
    viewModel{
        PlantsListViewModel(get())
    }
    viewModel {
        PlantDetailViewModel(get())
    }
    viewModel {
        AddOrEditPlantViewModel(get())
    }
    viewModel {
        PlantsContextualViewModel(get(), get())
    }


    // Room ViewModels
    viewModel {
        RoomsListViewModel(get())
    }
    viewModel {
        RoomDetailViewModel(get())
    }
    viewModel {
        AddOrEditRoomViewModel(get())
    }


    // Alert ViewModels
    viewModel {
        AlertsViewModel(get(), get())
    }


    // Dialog fragments ViewModels
    viewModel {
        RoomsDialogFragmentViewModel(get())
    }
}