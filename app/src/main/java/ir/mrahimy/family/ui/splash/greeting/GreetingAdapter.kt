package ir.mrahimy.family.ui.splash.greeting

import androidx.recyclerview.widget.DiffUtil
import ir.mrahimy.family.R
import ir.mrahimy.family.base.BaseAdapter
import ir.mrahimy.family.data.pojo.Greeting

/**
 * Shows a list of greetings by [Greeting][ir.mrahimy.family.data.pojo.Greeting] data class
 */
class GreetingAdapter : BaseAdapter<Greeting>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Greeting>() {
            override fun areContentsTheSame(oldItem: Greeting, newItem: Greeting): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Greeting, newItem: Greeting): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_greeting
    }
}