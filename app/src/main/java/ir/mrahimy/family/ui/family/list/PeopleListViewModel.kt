package ir.mrahimy.family.ui.family.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import ir.mrahimy.family.base.BaseViewModel
import ir.mrahimy.family.data.pojo.Person
import ir.mrahimy.family.data.pojo.fillData
import kotlinx.coroutines.launch

class PeopleListViewModel(model: PeopleListModel) : BaseViewModel(model) {

    val peopleList: LiveData<List<Person>> = model.getAll().map { list ->
        list.map { it.fillData() }
    }

    init {
        viewModelScope.launch {
            model.syncPeople()
        }
    }
}