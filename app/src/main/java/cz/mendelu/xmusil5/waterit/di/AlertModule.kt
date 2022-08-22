package cz.mendelu.xmusil5.waterit.di

import android.content.Context
import cz.mendelu.xmusil5.waterit.alerts.AlertManager
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository
import org.koin.dsl.module


val alertModule = module {
    single {
        provideAlertManager(get(), get())
    }
}

fun provideAlertManager(repository: IPlantsLocalRepository, context: Context): AlertManager{
    return AlertManager(repository, context)
}