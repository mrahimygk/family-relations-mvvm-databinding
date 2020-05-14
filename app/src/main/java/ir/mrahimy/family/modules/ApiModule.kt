package ir.mrahimy.family.modules

import ir.mrahimy.family.network.api.PeopleApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val apiModule = module {
    factory<PeopleApi> { get<Retrofit>(KarafsAppApiServiceQualifier).create() }
}