package ir.mrahimy.family.repository

import ir.mrahimy.family.data.db.dao.PersonDao
import ir.mrahimy.family.data.pojo.Person
import ir.mrahimy.family.network.ApiResult
import ir.mrahimy.family.network.api.PeopleApi
import ir.mrahimy.family.network.util.safeApiCall

class PeopleRepositoryImpl(
    private val api: PeopleApi,
    private val dao: PersonDao
) : PeopleRepository {
    override suspend fun getAll(): ApiResult<List<Person>> = safeApiCall {
        return@safeApiCall ApiResult.Success(api.getPeople(mapOf()))
    }

    override suspend fun sync() =
        safeApiCall {
            val data = api.getPeople(mapOf())
            dao.insert(data)
            return@safeApiCall ApiResult.Success(data)
        }


    override fun getAllFromLocalDb() = dao.getAll()

    override fun getInferredRelations() = dao.inferRelations()
}