package ir.mrahimy.family.ui.family.list

import androidx.lifecycle.viewModelScope
import ir.mrahimy.family.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PeopleListViewModel(model: PeopleListModel) : BaseViewModel(model) {

    init {
        viewModelScope.launch {
            delay(500)
            navigateTo(PeopleListFragmentDirections.actionPeopleListToInferredData())
        }
    }
}