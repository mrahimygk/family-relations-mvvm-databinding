package ir.mrahimy.family.ui.family.list

import ir.mrahimy.family.base.BaseModel
import ir.mrahimy.family.repository.PeopleRepository

class PeopleListModel(private val peopleRepository: PeopleRepository) : BaseModel() {

    fun getAll() = peopleRepository.getAllFromLocalDb()

    fun getInferredRelations() = peopleRepository.getInferredRelations()

    suspend fun syncPeople() = peopleRepository.sync()
}