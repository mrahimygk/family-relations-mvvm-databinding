package ir.mrahimy.family.modules

import ir.mrahimy.family.ui.splash.greeting.GreetingAdapter
import org.koin.dsl.module

val adapterModule = module {
    factory { GreetingAdapter() }
}