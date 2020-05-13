package ir.mrahimy.family.modules

import ir.mrahimy.family.ui.splash.SplashViewModel
import ir.mrahimy.family.ui.splash.greeting.GreetingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { GreetingViewModel(get()) }
}