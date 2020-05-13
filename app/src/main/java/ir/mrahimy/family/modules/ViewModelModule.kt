package ir.mrahimy.family.modules

import ir.mrahimy.family.ui.family.FamilyViewModel
import ir.mrahimy.family.ui.family.list.PeopleListViewModel
import ir.mrahimy.family.ui.family.relations.FamilyRelationsViewModel
import ir.mrahimy.family.ui.splash.SplashViewModel
import ir.mrahimy.family.ui.splash.greeting.GreetingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { GreetingViewModel(get()) }
    viewModel { FamilyViewModel(get()) }
    viewModel { PeopleListViewModel(get()) }
    viewModel { FamilyRelationsViewModel(get()) }
}