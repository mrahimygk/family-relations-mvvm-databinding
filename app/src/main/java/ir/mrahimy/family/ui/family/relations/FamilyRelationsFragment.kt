package ir.mrahimy.family.ui.family.relations

import ir.mrahimy.family.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ir.mrahimy.family.R
import ir.mrahimy.family.databinding.FragmentFamilyRelationsBinding
import ir.mrahimy.family.ui.family.FamilyViewModel
import kotlinx.android.synthetic.main.fragment_family_relations.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FamilyRelationsFragment :
    BaseFragment<FamilyRelationsViewModel, FragmentFamilyRelationsBinding>() {
    override val viewModel: FamilyRelationsViewModel by viewModel()
    override val layoutRes: Int = R.layout.fragment_family_relations
    override val sharedViewModel: FamilyViewModel by sharedViewModel()
    private val peopleRelationsAdapter: PeopleRelationsAdapter by inject()

    override fun configEvents() {
        people_relations_recyclerview?.adapter = peopleRelationsAdapter
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