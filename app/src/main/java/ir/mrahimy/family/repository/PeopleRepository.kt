package ir.mrahimy.family.repository

import androidx.lifecycle.LiveData
import ir.mrahimy.family.data.pojo.Person
import ir.mrahimy.family.network.ApiResult

interface PeopleRepository {
    suspend fun getAll(): ApiResult<List<Person>>
    suspend fun sync(): ApiResult<List<Person>>
    fun getAllFromLocalDb(): LiveData<List<Person>>
}