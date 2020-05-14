package ir.mrahimy.family.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import ir.mrahimy.family.R
import ir.mrahimy.family.application.AppTheme
import ir.mrahimy.family.application.FamilyAppController
import ir.mrahimy.family.network.util.ConnectionLiveData
import ir.mrahimy.family.util.EventObserver
import ir.mrahimy.family.util.NavigationCommand
import ir.mrahimy.family.util.interfaces.BackPressListenerFragment
import ir.mrahimy.family.util.ktx.navigateUpOrFinish

/**
 * Base abstract class which is the parent of every activity in this project.
 *
 * All Activities have to extend this class instead of [androidx.appcompat.app.AppCompatActivity]
 *      or [android.app.Activity] (which are the parents of this class)
 *
 * All Activities ought to have a ViewModel extending [BaseViewModel] and a DataBinding class
 *      which is described in their corresponding classes
 *
 * Obligatory to be overridden:
 *
 *      viewModel: VM
 *      layoutRes: Int
 *      navigationId: Int
 *
 *      configEvents()
 *      bindObservables()
 *      initBinding()
 *
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : AppCompatActivity() {

    var currentTheme = AppTheme.DARK
        private set

    /**
     * Every activity should override this Generic field by defining the class name and using koin
     *      to inject the object
     */
    abstract val viewModel: VM

    /**
     * By overriding the layoutRes you can define the xml layout
     */
    abstract val layoutRes: Int

    /**
     * This is the id of [NavHostFragment][androidx.navigation.fragment.NavHostFragment] that has an app:navGraph
     * This is needed in [BaseViewModel] to be able to perform navigation inside viewModels
     */
    abstract val navigationId: Int

    /**
     * Initialising binding lazily means it will only be initialized on access, not before
     */
    val binding by lazy {
        DataBindingUtil.setContentView(this, layoutRes) as DB
    }

    /**
     * No activity extending this class should override onCreate(),
     * No need to get the current theme at first, but it enhances the performance
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCurrentTheme()
        initBinding()
        configEvents()
        bindObservables()
        listenOnInternetConnectivity()
        observeNavigation()
    }

    /**
     * Utilizing the command pattern makes it easy to have the navigation handling
     *      inside a single method
     *
     * A class extending BaseViewModel has access to navigate*() functions
     */
    private fun observeNavigation() {
        viewModel.navigationCommand.observe(this, EventObserver { command ->
            val navController = Navigation.findNavController(this, navigationId)
            when (command) {
                is NavigationCommand.To ->
                    navController.navigate(command.directions)
                is NavigationCommand.ToWithFinish -> {
                    navController.navigate(command.directions)
                    finish()
                }
                is NavigationCommand.ToAction ->
                    navController.navigate(command.actionId)
                is NavigationCommand.ToActionWithFinish -> {
                    navController.navigate(command.actionId)
                    finish()
                }
                is NavigationCommand.Back ->
                    navController.navigateUpOrFinish(this)
                is NavigationCommand.BackTo ->
                    navController.popBackStack(command.destinationId, command.inclusive)
                is NavigationCommand.ToRoot -> {
                    navController.popBackStack(R.id.home, false)
                }
            }
        })
    }

    /**
     * getting current theme by the index saved in shp.
     */
    private fun getCurrentTheme() {
        applyTheme(AppTheme.LIGHT)
    }


    /**
     * Observes on internet connectivity. Can shows an alert on top status bar if net is not available.
     */
    private fun listenOnInternetConnectivity() {
        ConnectionLiveData.observe(this, Observer {
            if (it.isConnected)
            //TODO: hide 'no internet' alert
            else
                lifecycleScope.launchWhenResumed {
                    //TODO: show no internet alert
                }
        })
    }

    /**
     * Events like adapter lambdas should be inside this overridden method
     */
    abstract fun configEvents()

    /**
     * observing on viewModel's live data should be inside this overridden method
     */
    abstract fun bindObservables()

    /**
     * override this in children in case of needing to fetch data again
     */
    open fun onNetworkActivated() {}

    /**
     *
     *  You need override this method.
     *  And you need to set viewModel to binding: binding.viewModel = viewModel
     *
     */
    abstract fun initBinding()

    override fun onResume() {
        super.onResume()
        (application as? FamilyAppController)?.let {
            it.currentActivity = this
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun applyTheme(theme: AppTheme) {
        if (currentTheme == theme) return
        when (theme) {
            AppTheme.LIGHT -> setTheme(R.style.AppTheme)
            AppTheme.DARK -> setTheme(R.style.AppTheme_Dark)
        }
        currentTheme = theme
    }

    override fun onBackPressed() {
        val currentFragment = getCurrentFragment()
        if (currentFragment !is BackPressListenerFragment) super.onBackPressed()
        (currentFragment as? BackPressListenerFragment)?.onBackPressed()?.let {
            if (it) super.onBackPressed()
        }
    }

    protected fun getCurrentFragment(): Fragment? {
        val navHostFragment = supportFragmentManager.findFragmentById(navigationId)
        val currentFragments = navHostFragment?.childFragmentManager?.fragments
        return currentFragments?.let {
            if (it.isNotEmpty()) it[0]
            else null
        }
    }

}
