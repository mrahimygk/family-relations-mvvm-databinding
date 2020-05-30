package ir.mrahimy.family.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.mrahimy.family.BR

abstract class BaseAdapter<T>(
    private var itemSimilarityPredicate: (T, T) -> Boolean,
    private var contentSimilarityPredicate: (T, T) -> Boolean
) : ListAdapter<T, BaseAdapter<T>.DataBindingViewHolder>(object : DiffUtil.ItemCallback<T>() {
    override fun areContentsTheSame(
        oldItem: T,
        newItem: T
    ) = contentSimilarityPredicate.invoke(oldItem, newItem)

    override fun areItemsTheSame(
        oldItem: T,
        newItem: T
    ) = itemSimilarityPredicate.invoke(oldItem, newItem)
}) {

    /**
     * these functions should be assigned inside configEvents() of the activity/fragment
     */
    var onItemClicked: ((item: T, view: View) -> Unit)? = null
    var onItemLongClicked: ((item: T, view: View) -> Boolean)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        return DataBindingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            )
        )
    }

    override fun submitList(list: MutableList<T>?) {
        super.submitList(if (list != null) ArrayList<T>(list).toMutableList() else null)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) =
        holder.bind(getItem(position))

    abstract override fun getItemViewType(position: Int): Int

    override fun onViewAttachedToWindow(holder: DataBindingViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAppear()
    }

    override fun onViewDetachedFromWindow(holder: DataBindingViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDisappear()
    }

    /**
     * Being a [LifecycleOwner][androidx.lifecycle.LifecycleOwner] makes it possible to
     * bind edit texts inside adapter items.
     */
    inner class DataBindingViewHolder(
        private val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root), LifecycleOwner {

        private val lifecycleRegistry = LifecycleRegistry(this)

        init {
            lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
        }

        fun onAppear() {
            lifecycleRegistry.currentState = Lifecycle.State.CREATED
            lifecycleRegistry.currentState = Lifecycle.State.STARTED
        }

        fun onDisappear() {
            lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        }

        override fun getLifecycle(): Lifecycle {
            return lifecycleRegistry
        }

        fun bind(item: T) {
            binding.apply {
                lifecycleOwner = this@DataBindingViewHolder
                setVariable(BR.item, item)
                executePendingBindings()
                root.apply {
                    setOnClickListener {
                        onItemClicked?.invoke(item, this)
                    }

                    setOnLongClickListener {
                        return@setOnLongClickListener onItemLongClicked?.invoke(item, this)
                            ?: return@setOnLongClickListener true
                    }
                }
            }
        }
    }
}