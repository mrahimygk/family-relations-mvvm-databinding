package ir.mrahimy.family.ui.family.relations

import ir.mrahimy.family.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ir.mrahimy.family.R
import ir.mrahimy.family.databinding.FragmentFamilyRelationsBinding
import ir.mrahimy.family.ui.family.FamilyViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FamilyRelationsFragment : BaseFragment<FamilyRelationsViewModel, FragmentFamilyRelationsBinding>() {
    override val viewModel: FamilyRelationsViewModel by viewModel()
    override val layoutRes: Int = R.layout.fragment_family_relations
    override val sharedViewModel: FamilyViewModel by sharedViewModel()

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