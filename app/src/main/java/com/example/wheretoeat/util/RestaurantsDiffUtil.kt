package com.example.wheretoeat.util

import androidx.recyclerview.widget.DiffUtil
import com.example.wheretoeat.model.Restaurant

// compares the old list with the new data
class RestaurantsDiffUtil<T>(
    private val oldList: List<T>,
    private val newList: List<T>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition] // reference comparison
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}