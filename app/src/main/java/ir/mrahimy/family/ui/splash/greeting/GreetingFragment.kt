package ir.mrahimy.family.ui.splash.greeting

import ir.mrahimy.family.R
import ir.mrahimy.family.base.BaseFragment
import ir.mrahimy.family.databinding.FragmentGreetingBinding
import ir.mrahimy.family.ui.splash.SplashViewModel
import kotlinx.android.synthetic.main.fragment_greeting.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GreetingFragment : BaseFragment<GreetingViewModel, FragmentGreetingBinding>() {
    override val viewModel: GreetingViewModel by viewModel()
    override val layoutRes: Int = R.layout.fragment_greeting
    override val sharedViewModel: SplashViewModel by sharedViewModel()

    private val greetingAdapter: GreetingAdapter by inject()

    override fun configEvents() {
        greetings_recyclerview?.adapter = greetingAdapter
        greetingAdapter.onItemClicked = { greeting, _ ->
            viewModel.onGreetingClick(greeting)
        }
    }

    override fun bindObservables() {
//        TODO("Not yet implemented")
    }

    override fun initBinding() {
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            svm = sharedViewModel
            executePendingBindings()
        }
    }
}