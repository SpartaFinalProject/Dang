package com.android.dang.like

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class Swipe(private val mAdapter: LikeAdapter) : ItemTouchHelper.SimpleCallback(0, RIGHT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        mAdapter.removeData(viewHolder.layoutPosition)
        val position = viewHolder.layoutPosition
        val data = mAdapter.Data(position)

        mAdapter.removeData(viewHolder.layoutPosition)
        Snackbar.make(viewHolder.itemView, "삭제 되었습니다", Snackbar.LENGTH_LONG).setAction("되돌리기") {
            mAdapter.insertData(position, data)
        }.show()
    }
}