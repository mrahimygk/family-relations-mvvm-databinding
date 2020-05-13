package ir.mrahimy.family.base

import android.app.Application
import androidx.annotation.StringRes
import ir.mrahimy.family.application.FamilyAppController

/**
 * Base abstract class which is the parent of every ViewModel in this project that needs to use a
 *      reference of the application class. All those ViewModels that have to extend this class
 *      to be able to work with Activities & fragments
 *
 * This class handles navigation command pattern event passing,
 *      the actual action is consumed in [BaseActivity][observeNavigation]
 *
 * Users have to use getString() for accessing string resources
 * not to hardcode a string or inject application or use a context
 *
 */
abstract class BaseAndroidViewModel(
    private val application: Application,
    private val model: BaseModel
) :
    BaseViewModel(model) {

    protected fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String {
        return getApplication().resources.getString(resId, *formatArgs)
    }

    protected fun getApplication() = application as FamilyAppController
}