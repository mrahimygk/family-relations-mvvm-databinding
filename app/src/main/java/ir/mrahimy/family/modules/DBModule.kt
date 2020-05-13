package ir.mrahimy.family.modules

import androidx.room.Room
import ir.mrahimy.family.data.db.FamilyDb
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

const val DB_NAME = "application_db"

/**
 * Application level dependencies via Koin DI
 * There should be just database-related definitions here in this file.
 * Other classes should be defined in the other files on this package.
 *
 * Using [factory][org.koin.core.module.Module.factory] means there is a new instance needed on
 * every [inject()][org.koin.android.ext.android.inject]
 *
 * Using [single][org.koin.core.module.Module.single] means there is just one instance needed
 */
val dbModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            FamilyDb::class.java,
            DB_NAME
        )
            .fallbackToDestructiveMigration()
//            .addMigrations()
            .build()
    }

    factory {
        get<FamilyDb>().personDao()
    }
}