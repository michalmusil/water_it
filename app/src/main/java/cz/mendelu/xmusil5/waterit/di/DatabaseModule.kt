package cz.mendelu.xmusil5.waterit.di

import android.content.Context
import cz.mendelu.xmusil5.waterit.database.WateritDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        provideDatabase(androidContext())
    }
}

fun provideDatabase(context: Context): WateritDatabase{
    return WateritDatabase.getDatabase(context)
}