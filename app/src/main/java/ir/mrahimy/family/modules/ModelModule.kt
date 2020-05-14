package ir.mrahimy.family.modules

import ir.mrahimy.family.ui.family.FamilyModel
import ir.mrahimy.family.ui.family.list.PeopleListModel
import ir.mrahimy.family.ui.family.relations.FamilyRelationsModel
import ir.mrahimy.family.ui.splash.SplashModel
import ir.mrahimy.family.ui.splash.greeting.GreetingModel
import org.koin.dsl.module

val modelModule = module {
    factory { SplashModel() }
    factory { GreetingModel() }
    factory { PeopleListModel(get()) }
    factory { FamilyModel() }
    factory { FamilyRelationsModel() }
}