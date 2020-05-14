package ir.mrahimy.family.ui.family.relations

import androidx.recyclerview.widget.DiffUtil
import ir.mrahimy.family.R
import ir.mrahimy.family.base.BaseAdapter
import ir.mrahimy.family.data.pojo.PersonRelations

/**
 * Shows a list of people related to a [Person][ir.mrahimy.family.data.pojo.Person]
 */
class PeopleRelationsAdapter : BaseAdapter<PersonRelations>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PersonRelations>() {
            override fun areContentsTheSame(
                oldItem: PersonRelations,
                newItem: PersonRelations
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: PersonRelations,
                newItem: PersonRelations
            ): Boolean {
                return oldItem.person.firstName == newItem.person.firstName && oldItem.person.lastName == newItem.person.lastName
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_person_relations
    }
}