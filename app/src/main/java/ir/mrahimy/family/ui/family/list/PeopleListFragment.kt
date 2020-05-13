package ir.mrahimy.family.ui.family.list

import ir.mrahimy.family.R
import ir.mrahimy.family.base.BaseFragment
import ir.mrahimy.family.databinding.FragmentPeopleListBinding
import ir.mrahimy.family.ui.family.FamilyViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PeopleListFragment : BaseFragment<PeopleListViewModel, FragmentPeopleListBinding>() {
    override val viewModel: PeopleListViewModel by viewModel()
    override val layoutRes: Int = R.layout.fragment_people_list
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
            executePendingBindings()
        }
    }
}