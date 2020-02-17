package com.death.hubble.util.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


/**
 * ItemDecoration for staggered grid view, to separate the cards evenly in recycler view
 *
 */
class ItemDecoration(space: Int) :
    RecyclerView.ItemDecoration() {

    private val halfSpace: Int = space / 2
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.paddingLeft != halfSpace) {
            parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace)
            parent.clipToPadding = false
        }

        outRect.top = halfSpace
        outRect.bottom = 0
        outRect.left = halfSpace
        outRect.right = halfSpace
    }

}