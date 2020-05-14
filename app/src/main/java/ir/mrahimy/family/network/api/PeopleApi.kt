package ir.mrahimy.family.network.api

import ir.mrahimy.family.data.pojo.Person
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface PeopleApi {

    @GET("android-test/")
    suspend fun getPeople(@QueryMap options: Map<String, String>): List<Person>
}