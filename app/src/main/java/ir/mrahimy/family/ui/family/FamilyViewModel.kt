package ir.mrahimy.family.ui.family

import androidx.lifecycle.MutableLiveData
import ir.mrahimy.family.base.BaseViewModel
import ir.mrahimy.family.data.pojo.PersonRelations
import ir.mrahimy.family.util.Shared

class FamilyViewModel(model: FamilyModel) : BaseViewModel(model) {
    /**
     * getting inferred list by db query. This is in the exact Karaf's code challenge requirements
     */
    val inferredList = MutableLiveData<List<String>>()

    @Shared("inferredPeopleRelations")
    val inferredPeopleRelations = MutableLiveData<List<PersonRelations>>()
}