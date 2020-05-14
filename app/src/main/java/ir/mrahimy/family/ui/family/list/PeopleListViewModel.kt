package ir.mrahimy.family.ui.family.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import ir.mrahimy.family.R
import ir.mrahimy.family.base.BaseViewModel
import ir.mrahimy.family.data.pojo.Person
import ir.mrahimy.family.data.pojo.SnackMessageAction
import ir.mrahimy.family.data.pojo.SnackMessage
import ir.mrahimy.family.data.pojo.fillData
import ir.mrahimy.family.network.ApiResult
import ir.mrahimy.family.util.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PeopleListViewModel(private val model: PeopleListModel) : BaseViewModel(model) {

    /**
     * getting person list from db only, means our single source of truth is out local db which
     * it's data is synced by api. This is in the Karaf's code challenge requirements
     */
    val peopleList: LiveData<List<Person>> = model.getAll().map { list ->
        list.map { it.fillData() }
    }

    private val _isLoadingPeopleList = MutableLiveData<Boolean>(false)
    val isLoadingPeopleList: LiveData<Boolean>
        get() = _isLoadingPeopleList

    init {
        viewModelScope.launch {
            syncPeople(1)
        }
    }

    /**
     * This method asks model to get person list from karafs api and put then into database
     * if the result has errors, it asks for a sync a second time.
     */
    private suspend fun syncPeople(tryCount: Int) {
        _isLoadingPeopleList.postValue(true)
        if (tryCount > 3) {
            val snackMessageCapsule = SnackMessage(
                R.string.max_try_please_check,
                Snackbar.LENGTH_INDEFINITE,
                SnackMessageAction(R.string.retry) {
                    viewModelScope.launch {
                        syncPeople(1)
                    }
                }
            )
            _snackMessage.postValue(Event(snackMessageCapsule))
            _isLoadingPeopleList.postValue(false)
            return
        }
        when (val result = model.syncPeople()) {
            is ApiResult.Error -> {
                /**
                 * Maybe log to error report?
                 * Then sync data again after a delay
                 */
                delay(333)
                syncPeople(tryCount + 1)
            }

            /**
             * ApiResult.Success can be ignored for now, the result is parsed in peopleRepository.
             */
            is ApiResult.Success -> _isLoadingPeopleList.postValue(false)

        }
    }
}