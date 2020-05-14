package ir.mrahimy.family.modules

import ir.mrahimy.family.repository.PeopleRepository
import ir.mrahimy.family.repository.PeopleRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<PeopleRepository> { PeopleRepositoryImpl(get(), get()) }
}