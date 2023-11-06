package com.android.dang.like

import android.util.Log
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
        val position = viewHolder.layoutPosition
        Log.e("swipe","position: $position")
        val data = mAdapter.data(position)

        mAdapter.removeData(viewHolder.layoutPosition)
        Snackbar.make(viewHolder.itemView, "삭제 되었습니다", Snackbar.LENGTH_SHORT).setAction("되돌리기") {
            mAdapter.insertData(position, data)
        }.show()
    }
}