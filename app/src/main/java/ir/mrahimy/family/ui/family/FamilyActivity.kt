package ir.mrahimy.family.ui.family

import androidx.navigation.navArgs
import ir.mrahimy.family.R
import ir.mrahimy.family.base.BaseActivity
import ir.mrahimy.family.databinding.ActivityFamilyBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FamilyActivity : BaseActivity<FamilyViewModel, ActivityFamilyBinding>() {
    override val viewModel: FamilyViewModel by viewModel()
    override val layoutRes: Int = R.layout.activity_family
    override val navigationId: Int = R.id.navigation_fragment
    private val args: FamilyActivityArgs by navArgs()

    override fun configEvents() {
        /**
         * setting activity title by the input nav args
         */
        title = args.greeting.title
    }

    override fun bindObservables() {
//        TODO("Not yet implemented")
    }

    override fun initBinding() {
        binding.apply {
            lifecycleOwner = this@FamilyActivity
            vm = viewModel
            executePendingBindings()
        }
    }

}
