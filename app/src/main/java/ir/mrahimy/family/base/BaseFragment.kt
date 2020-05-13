package ir.mrahimy.family.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import ir.mrahimy.family.R
import ir.mrahimy.family.util.EventObserver
import ir.mrahimy.family.util.NavigationCommand
import ir.mrahimy.family.util.SharedAnnotation
import ir.mrahimy.family.util.ktx.navigateUpOrFinish
import ir.mrahimy.family.util.ktx.showSnackBar
import ir.mrahimy.family.util.sharedAnnotation

/**
 * Base abstract class which is the parent of every fragment in this project.
 *
 * All Fragments have to extend this class instead of [androidx.fragment.app.Fragment]
 *      (which is the parent of this class)
 *
 * All Fragments ought to have a ViewModel extending [BaseViewModel] and a DataBinding class
 *      which is described in their corresponding classes
 *
 * Obligatory to be overridden:
 *
 *      viewModel: VM
 *      layoutRes: Int
 *
 *      configEvents()
 *      bindObservables()
 *      initBinding()
 *
 * Optional to override:
 *      sharedViewModel: BaseViewModel
 *
 */
abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {

    abstract val viewModel: VM
    open val sharedViewModel: BaseViewModel? = null
    open var binding: DB? = null

    private val sharedAnnotation: SharedAnnotation by sharedAnnotation()

    private fun init(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
    }

    abstract val layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        init(inflater, container)
        initBinding()
        bindParentObservables()
        bindObservables()
        return binding?.root ?: View(context)
    }

    /**
     * Observing on events that is needed by every free fragment in Westeros.
     */
    private fun bindParentObservables() {
        viewModel.snackMessage.observe(viewLifecycleOwner, EventObserver {
            view?.showSnackBar(it)
        })

        viewModel.snackMessageString.observe(viewLifecycleOwner, EventObserver {
            view?.showSnackBar(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configEvents()
    }

    override fun onStart() {
        super.onStart()
        sharedAnnotation.share(this, viewModel, sharedViewModel)
    }

    override fun onStop() {
        super.onStop()
        sharedAnnotation.remove()
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
     *
     *  You need override this method.
     *  And you need to set viewModel to binding: binding.viewModel = viewModel
     *
     */
    abstract fun initBinding()

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeNavigation()
    }

    private fun observeNavigation() {
        viewModel.navigationCommand.observe(viewLifecycleOwner, EventObserver { command ->
            navigate(command)
        })
        sharedViewModel?.navigationCommand?.observe(viewLifecycleOwner, EventObserver { command ->
            navigate(command)
        })
    }

    private fun navigate(command: NavigationCommand) {
        when (command) {
            is NavigationCommand.To ->
                findNavController().navigate(command.directions)
            is NavigationCommand.ToWithFinish -> {
                findNavController().navigate(command.directions)
                activity?.finish()
            }
            is NavigationCommand.ToAction ->
                findNavController().navigate(command.actionId)
            is NavigationCommand.ToActionWithFinish -> {
                findNavController().navigate(command.actionId)
                activity?.finish()
            }
            is NavigationCommand.Back ->
                findNavController().navigateUpOrFinish(activity)
            is NavigationCommand.BackTo ->
                findNavController().popBackStack(command.destinationId, command.inclusive)
            is NavigationCommand.ToRoot -> {
                if (findNavController().popBackStack(R.id.home, false).not()) {
                    command.popNavIdOnFail?.let {
                        findNavController().navigate(
                            R.id.home,
                            null,
                            NavOptions.Builder().setPopUpTo(it, true).build()
                        )
                    }
                }
            }
        }
    }
}

