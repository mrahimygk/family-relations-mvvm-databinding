package ir.mrahimy.family.ui.family.list

import androidx.recyclerview.widget.DiffUtil
import ir.mrahimy.family.R
import ir.mrahimy.family.base.BaseAdapter
import ir.mrahimy.family.data.pojo.Person

/**
 * Shows a list of people by [Person][ir.mrahimy.family.data.pojo.Person] data class
 */
class PeopleAdapter : BaseAdapter<Person>(
    { oldItem, newItem -> oldItem == newItem },
    { oldItem, newItem ->
        oldItem.firstName == newItem.firstName && oldItem.lastName == newItem.lastName
    }) {

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_person
    }
}