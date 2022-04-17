package cz.mendelu.xmusil5.waterit.di

import cz.mendelu.xmusil5.waterit.database.WateritDatabase
import cz.mendelu.xmusil5.waterit.database.daos.PlantsDao
import cz.mendelu.xmusil5.waterit.database.daos.RoomsDao
import org.koin.dsl.module

val daoModule= module {
    single {
        providePlantsDao(get())
    }
    single {
        provideRoomsDao(get())
    }
}

fun provideRoomsDao(database: WateritDatabase): RoomsDao{
    return database.roomsDao()
}

fun providePlantsDao(database: WateritDatabase): PlantsDao{
    return database.plantsDao()
}