package com.innerhour.exercise.core.displayables

import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * Created by Suyash Chavan.
 */
@Parcelize
class DisplayableList<T> : Displayable {

    @IgnoredOnParcel
    private var items: ArrayList<T> = arrayListOf()

    val length: Int
        get() = items.size

    fun add(item: T) {
        items.add(item)
    }

    fun add(index: Int, item: T) {
        if (index > -1 && index < items.size) {
            items.add(index, item)
        } else {
            throw ArrayIndexOutOfBoundsException("the size of map is " + items.size + " and made attempt for index=" + index)
        }
    }

    operator fun get(position: Int): T {
        return items[position]
    }

    fun getItems(): ArrayList<T> {
        return items
    }

    fun addItems(list: List<T>) {
        items.addAll(list)
    }
}