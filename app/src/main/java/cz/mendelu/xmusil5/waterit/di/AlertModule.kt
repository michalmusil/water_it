package cz.mendelu.xmusil5.waterit.di

import cz.mendelu.xmusil5.waterit.alerts.AlertManager
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository
import org.koin.dsl.module


val alertModule = module {
    single {
        provideAlertManager(get())
    }
}

fun provideAlertManager(repository: IPlantsLocalRepository): AlertManager{
    return AlertManager(repository)
}