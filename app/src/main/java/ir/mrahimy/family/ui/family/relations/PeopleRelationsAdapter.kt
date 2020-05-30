package ir.mrahimy.family.ui.family.relations

import ir.mrahimy.family.R
import ir.mrahimy.family.base.BaseAdapter
import ir.mrahimy.family.data.pojo.PersonRelations

/**
 * Shows a list of people related to a [Person][ir.mrahimy.family.data.pojo.Person]
 */
class PeopleRelationsAdapter :
    BaseAdapter<PersonRelations>(
        { oldContent, newContent -> oldContent == newContent },
        { oldItem, newItem -> oldItem.person.firstName == newItem.person.firstName && oldItem.person.lastName == newItem.person.lastName }) {

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_person_relations
    }
}