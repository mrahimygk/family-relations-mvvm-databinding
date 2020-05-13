package ir.mrahimy.family.ui.splash

import ir.mrahimy.family.R
import ir.mrahimy.family.base.BaseActivity
import ir.mrahimy.family.databinding.ActivitySplashBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {
    override val viewModel: SplashViewModel by viewModel()
    override val layoutRes = R.layout.activity_splash

    override val navigationId: Int = R.id.splash_navigation

    override fun configEvents() {
//        TODO("Not yet implemented")
    }

    override fun bindObservables() {
//        TODO("Not yet implemented")
    }

    override fun initBinding() {
        binding.apply {
            lifecycleOwner = this@SplashActivity
            vm = viewModel
            executePendingBindings()
        }
    }
}