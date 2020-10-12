package com.nandra.covid19id.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CustomItemDecorator(private val horizontalSpace: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        parent.adapter?.run {
            if (this.itemCount >= 1) {
                when {
                    parent.getChildAdapterPosition(view) == 0 -> {
                        outRect.right = horizontalSpace
                    }
                    parent.getChildAdapterPosition(view) == (this.itemCount-1) -> {
                        outRect.left = horizontalSpace
                    }
                    else -> {
                        outRect.right = horizontalSpace
                        outRect.left = horizontalSpace
                    }
                }
            }
        }
    }
}
