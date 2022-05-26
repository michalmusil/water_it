package cz.mendelu.xmusil5.waterit

import android.app.Application
import cz.mendelu.xmusil5.waterit.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WateritApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(applicationContext)
            modules(databaseModule, daoModule, repositoryModule, viewModelModule, alertModule)
        }
    }
}