package ir.mrahimy.family.application

import android.app.Activity
import android.app.Application
import ir.mrahimy.family.BuildConfig
import ir.mrahimy.family.modules.adapterModule
import ir.mrahimy.family.modules.modelModule
import ir.mrahimy.family.modules.networkModule
import ir.mrahimy.family.modules.viewModelModule
import ir.mrahimy.family.network.util.ConnectionLiveData
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

/**
 * Custom application class
 */
class FamilyAppController : Application() {

    var currentActivity: Activity? = null

    override fun onCreate() {
        super.onCreate()

        /**
         * Planting Timber logger in debug mode
         */
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        //Network Connectivity
        ConnectionLiveData.init(this)

        /**
         * all injection modules should be added here as a koin module.
         */
        startKoin {
            androidContext(this@FamilyAppController)
            androidLogger(Level.DEBUG)
            modules(
                viewModelModule, modelModule, adapterModule,
                networkModule
            )
        }
    }
}