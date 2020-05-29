package ir.mrahimy.family.ui.family.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import ir.mrahimy.family.R
import ir.mrahimy.family.base.BaseViewModel
import ir.mrahimy.family.data.pojo.*
import ir.mrahimy.family.network.ApiResult
import ir.mrahimy.family.util.Shared
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

    /**
     * getting inferred list by db query. This is in the exact Karaf's code challenge requirements
     */
    val inferredList: MutableLiveData<List<String>> =
        model.getInferredRelations().map { it } as MutableLiveData<List<String>>

    /**
     * points.zip(points.leftShift(1)) { a, b ->
    Pair(Vector2(a.bounds.x, a.bounds.y), Vector2(b.bounds.x, b.bounds.y))
    }
     */
    @Shared("inferredPeopleRelations")
    val inferredPeopleRelations: MutableLiveData<List<PersonRelations>> =
        peopleList.map { peopleList ->
            val relationsList = mutableListOf<PersonRelations>()
            peopleList.forEach { personOne ->
                val personOneRelations = mutableListOf<Person>()
                peopleList.forEach inner@{ personTwo ->
                    if (personOne == personTwo) return@inner
                    val haveSameFamilyName = personTwo.lastName == personOne.lastName
                    val isPartOfOtherFamilyName =
                        personTwo.lastName.contains(personOne.lastName) || personOne.lastName.contains(
                            personTwo.lastName
                        )
                    val haveSharedPart =
                        personOne.lastName.split("-").intersect(personTwo.lastName.split("-"))
                            .isNotEmpty()
                    if (haveSameFamilyName || isPartOfOtherFamilyName || haveSharedPart) {
                        personOneRelations.add(personTwo)
                    }
                }
                relationsList.add(PersonRelations(personOne, personOneRelations).fillText())
            }

            relationsList.toList()

        } as MutableLiveData<List<PersonRelations>>

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
            showSnackMessage(
                R.string.max_try_please_check,
                Snackbar.LENGTH_INDEFINITE,
                SnackMessageAction(R.string.retry) {
                    viewModelScope.launch {
                        syncPeople(1)
                    }
                })
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

    fun showRelations() {
        navigateTo(PeopleListFragmentDirections.actionPeopleListToInferredData())
    }

    private val mustExit = MutableLiveData<Boolean>().apply { value = false }
    fun onBackPress(): Boolean {
        /**
         * if data is loading, we prevent back pressing
         */
        if (_isLoadingPeopleList.value == true) {
            return false
        }

        if (mustExit.value == true) {
            return true
        }
        mustExit.postValue(true)
        showSnackMessage(R.string.press_back_button_again_to_exit)
        viewModelScope.launch {
            delay(1666)
            mustExit.postValue(false)
        }
        return false
    }
}