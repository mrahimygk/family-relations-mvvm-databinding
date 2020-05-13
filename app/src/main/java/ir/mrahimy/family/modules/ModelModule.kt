package ir.mrahimy.family.modules

import ir.mrahimy.family.ui.splash.SplashModel
import ir.mrahimy.family.ui.splash.greeting.GreetingModel
import org.koin.dsl.module

val modelModule = module {
    factory { SplashModel() }
    factory { GreetingModel() }
}