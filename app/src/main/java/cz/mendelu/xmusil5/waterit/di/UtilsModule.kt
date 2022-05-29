package cz.mendelu.xmusil5.waterit.di

import android.content.Context
import cz.mendelu.xmusil5.waterit.alerts.AlertManager
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository
import cz.mendelu.xmusil5.waterit.utils.LanguageManager
import org.koin.dsl.module


val utilsModule = module {
    single {
        provideLanguageManager(get())
    }
}

fun provideLanguageManager(context: Context): LanguageManager {
    return LanguageManager(context)
}