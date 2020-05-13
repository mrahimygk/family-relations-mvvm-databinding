package ir.mrahimy.family.ui.splash.greeting

import ir.mrahimy.family.R
import ir.mrahimy.family.base.BaseFragment
import ir.mrahimy.family.databinding.FragmentGreetingBinding
import ir.mrahimy.family.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GreetingFragment : BaseFragment<GreetingViewModel, FragmentGreetingBinding>() {
    override val viewModel: GreetingViewModel by viewModel()
    override val layoutRes: Int = R.layout.fragment_greeting
    override val sharedViewModel: SplashViewModel by sharedViewModel()

    override fun configEvents() {
//        TODO("Not yet implemented")
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