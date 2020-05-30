package ir.mrahimy.family.ui.splash.greeting

import ir.mrahimy.family.R
import ir.mrahimy.family.base.BaseAdapter
import ir.mrahimy.family.data.pojo.Greeting

/**
 * Shows a list of greetings by [Greeting][ir.mrahimy.family.data.pojo.Greeting] data class
 */
class GreetingAdapter : BaseAdapter<Greeting>(
    { oldItem, newItem -> oldItem == newItem },
    { oldItem, newItem -> oldItem.id == newItem.id }
) {
    override fun getItemViewType(position: Int): Int {
        return R.layout.item_greeting
    }
}