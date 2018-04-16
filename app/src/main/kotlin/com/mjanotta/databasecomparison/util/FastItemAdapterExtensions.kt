package com.mjanotta.databasecomparison.util

import android.support.v7.util.DiffUtil
import android.support.v7.util.ListUpdateCallback
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.commons.utils.DiffCallback
import com.mikepenz.fastadapter.utils.IdDistributor
import java.util.*


fun <T, A : ItemAdapter<Item>, Item : IItem<*, *>> A.calculateDiff(items: List<Item>, item: (Item) -> T, id: (T) -> Any): DiffUtil.DiffResult {
    if (isUseIdDistributor) {
        IdDistributor.checkIds(items)
    }

    fastAdapter.collapse(false)

    if (comparator != null) {
        Collections.sort(items, comparator)
    }

    mapPossibleTypes(items)

    val oldItems = adapterItems

    val callback = ItemDiffCallback(item, id)
    val result = DiffUtil.calculateDiff(FastAdapterCallback(oldItems, items, callback), true)

    if (items !== oldItems) {
        if (!oldItems.isEmpty()) {
            oldItems.clear()
        }

        oldItems.addAll(items)
    }

    return result
}

fun <A : ItemAdapter<Item>, Item : IItem<*, *>> A.setDiff(result: DiffUtil.DiffResult) {
    result.dispatchUpdatesTo(FastAdapterListUpdateCallback(this))
}

private class FastAdapterCallback<Item : IItem<*, *>> internal constructor(private val oldItems: List<Item>, private val items: List<Item>, private val callback: DiffCallback<Item>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return items.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return callback.areItemsTheSame(oldItems[oldItemPosition], items[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return callback.areContentsTheSame(oldItems[oldItemPosition], items[newItemPosition])
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val result = callback.getChangePayload(oldItems[oldItemPosition], oldItemPosition, items[newItemPosition], newItemPosition)
        return result ?: super.getChangePayload(oldItemPosition, newItemPosition)
    }
}

private class FastAdapterListUpdateCallback<A : ItemAdapter<Item>, Item : IItem<*, *>> internal constructor(private val adapter: A) : ListUpdateCallback {

    override fun onInserted(position: Int, count: Int) {
        adapter.fastAdapter.notifyAdapterItemRangeInserted(adapter.fastAdapter.getPreItemCountByOrder(adapter.order) + position, count)
    }

    override fun onRemoved(position: Int, count: Int) {
        adapter.fastAdapter.notifyAdapterItemRangeRemoved(adapter.fastAdapter.getPreItemCountByOrder(adapter.order) + position, count)
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        adapter.fastAdapter.notifyAdapterItemMoved(adapter.fastAdapter.getPreItemCountByOrder(adapter.order) + fromPosition, toPosition)
    }

    override fun onChanged(position: Int, count: Int, payload: Any) {
        adapter.fastAdapter.notifyAdapterItemRangeChanged(adapter.fastAdapter.getPreItemCountByOrder(adapter.order) + position, count, payload)
    }
}

private class ItemDiffCallback<T, Item : IItem<*, *>> internal constructor(val item: (Item) -> T, val id: (T) -> Any) : DiffCallback<Item> {

    override fun getChangePayload(oldItem: Item, oldItemPosition: Int, newItem: Item, newItemPosition: Int): Any? {
        return null
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return item(oldItem) == item(newItem)
    }

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return id(item(oldItem)) == id(item(newItem))
    }
}