package ir.mrahimy.family.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import ir.mrahimy.family.util.Event
import ir.mrahimy.family.util.NavigationCommand

/**
 * Base abstract class which is the parent of every ViewModel in this project.
 *
 * All ViewModels have to extend this class to be able to work with Activities & fragments
 *
 * This class handles navigation command pattern event passing,
 *      the actual action is consumed in [BaseActivity] method 'observeNavigation'
 *
 */
abstract class BaseViewModel(private val model: BaseModel) : ViewModel() {

    /** Used in situations when we need to navigate to another fragment/activity
     */
    val navigationCommand =  MutableLiveData<Event<NavigationCommand>>()

    /**
     *      getting directions from the navigation's built actions
     */
    fun navigateTo(directions: NavDirections) {
        navigationCommand.postValue(Event(NavigationCommand.To(directions)))
    }

    /**
     *      getting directions from the navigation's built actions
     *      finishing the current activity afterwards
     */
    fun navigateToWithFinish(directions: NavDirections) {
        navigationCommand.postValue(Event(NavigationCommand.ToWithFinish(directions)))
    }

    /**
     *      getting directions from the navGraph's id
     */
    fun navigateTo(actionId: Int) {
        navigationCommand.postValue(Event(NavigationCommand.ToAction(actionId)))
    }

    /**
     *      getting directions from the navGraph's id
     *      finishing the current activity afterwards
     */
    fun navigateToWithFinish(actionId: Int) {
        navigationCommand.postValue(Event(NavigationCommand.ToActionWithFinish(actionId)))
    }

    /**
     * navigating back to the main navigation's home fragment
     */
    fun navigateToRoot(popNavIdOnFail: Int? = null) {
        navigationCommand.postValue(Event(NavigationCommand.ToRoot(popNavIdOnFail)))
    }

    /**
     * using [NavController][androidx.navigation.NavController]'s navigateUp()
     */
    fun navigateBack() {
        navigationCommand.postValue(Event(NavigationCommand.Back))
    }

    /**
     * using [NavController][androidx.navigation.NavController]'s popBackStack()
     */
    fun navigateBackTo(destinationId: Int, inclusive: Boolean) {
        navigationCommand.postValue(Event(NavigationCommand.BackTo(destinationId, inclusive)))
    }

}