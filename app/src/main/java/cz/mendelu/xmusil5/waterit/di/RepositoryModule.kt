package cz.mendelu.xmusil5.waterit.di

import cz.mendelu.xmusil5.waterit.database.daos.PlantsDao
import cz.mendelu.xmusil5.waterit.database.daos.RoomsDao
import cz.mendelu.xmusil5.waterit.database.repositories.plants.IPlantsLocalRepository
import cz.mendelu.xmusil5.waterit.database.repositories.plants.PlantsLocalRepositoryImpl
import cz.mendelu.xmusil5.waterit.database.repositories.rooms.IRoomsLocalRepository
import cz.mendelu.xmusil5.waterit.database.repositories.rooms.RoomsLocalRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single{
        providePlantsLocalRepository(get())
    }
    single {
        provideRoomsLocalRepository(get())
    }
}

fun provideRoomsLocalRepository(roomsDao: RoomsDao): IRoomsLocalRepository{
    return RoomsLocalRepositoryImpl(roomsDao)
}

fun providePlantsLocalRepository(plantsDao: PlantsDao): IPlantsLocalRepository{
    return PlantsLocalRepositoryImpl(plantsDao)
}