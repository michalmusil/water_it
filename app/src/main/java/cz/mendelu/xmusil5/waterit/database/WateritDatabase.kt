package cz.mendelu.xmusil5.waterit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cz.mendelu.xmusil5.waterit.database.daos.PlantsDao
import cz.mendelu.xmusil5.waterit.database.daos.RoomsDao
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom

@Database(entities = [DbRoom::class, DbPlant::class], version = 1, exportSchema = true)
abstract class WateritDatabase: RoomDatabase() {

    abstract fun roomsDao(): RoomsDao
    abstract fun plantsDao(): PlantsDao

    companion object {
        private var INSTANCE: WateritDatabase? = null
        fun getDatabase(context: Context): WateritDatabase {
            if (INSTANCE == null) {
                synchronized(WateritDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            WateritDatabase::class.java, "waterit_database"
                        ).fallbackToDestructiveMigration()// REMOVE IN PRODUCTION
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}