package cz.mendelu.xmusil5.waterit.di

import cz.mendelu.xmusil5.waterit.ui.addplant.AddPlantViewModel
import cz.mendelu.xmusil5.waterit.ui.plantdetail.PlantDetailViewModel
import cz.mendelu.xmusil5.waterit.ui.plants.PlantsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{
        PlantsViewModel(get())
    }
    viewModel {
        PlantDetailViewModel(get())
    }
    viewModel {
        AddPlantViewModel(get())
    }
}