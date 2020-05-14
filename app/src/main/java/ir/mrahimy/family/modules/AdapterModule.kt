package ir.mrahimy.family.modules

import ir.mrahimy.family.ui.family.list.PeopleAdapter
import ir.mrahimy.family.ui.family.relations.PeopleRelationsAdapter
import ir.mrahimy.family.ui.splash.greeting.GreetingAdapter
import org.koin.dsl.module

val adapterModule = module {
    factory { GreetingAdapter() }
    factory { PeopleAdapter() }
    factory { PeopleRelationsAdapter() }
}