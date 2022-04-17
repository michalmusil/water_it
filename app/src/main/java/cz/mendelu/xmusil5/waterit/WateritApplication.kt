package cz.mendelu.xmusil5.waterit

import android.app.Application
import cz.mendelu.xmusil5.waterit.di.daoModule
import cz.mendelu.xmusil5.waterit.di.databaseModule
import cz.mendelu.xmusil5.waterit.di.repositoryModule
import cz.mendelu.xmusil5.waterit.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WateritApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(applicationContext)
            modules(databaseModule, daoModule, repositoryModule, viewModelModule)
        }
    }
}