package ir.mrahimy.family.ui.family.list

import ir.mrahimy.family.R
import ir.mrahimy.family.base.BaseFragment
import ir.mrahimy.family.databinding.FragmentPeopleListBinding
import ir.mrahimy.family.ui.family.FamilyViewModel
import ir.mrahimy.family.util.interfaces.BackPressListenerFragment
import kotlinx.android.synthetic.main.fragment_people_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PeopleListFragment : BaseFragment<PeopleListViewModel, FragmentPeopleListBinding>(), BackPressListenerFragment {
    override val viewModel: PeopleListViewModel by viewModel()
    override val layoutRes: Int = R.layout.fragment_people_list
    override val sharedViewModel: FamilyViewModel by sharedViewModel()
    private val peopleAdapter: PeopleAdapter by inject()

    override fun configEvents() {
        people_recyclerview?.adapter = peopleAdapter
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

    override fun onBackPressed(): Boolean {
        return viewModel.onBackPress()
    }
}