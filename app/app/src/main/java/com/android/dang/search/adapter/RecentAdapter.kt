//package com.android.dang.search.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.android.dang.databinding.ItemRecyclerViewRecentWordBinding
//
//class RecentAdapter : RecyclerView.Adapter<RecentAdapter.Holder>() {
//
//    private var recentList = mutableListOf<String>()
//
//    interface ItemClick {
//
//    }
//
//    var itemClick: ItemClick? = null
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentAdapter.Holder {
//        val binding = ItemRecyclerViewRecentWordBinding.inflate(
//            LayoutInflater.from(parent.context),
//            parent,
//            false
//        )
//        return Holder(binding)
//    }
//
//    override fun onBindViewHolder(holder: RecentAdapter.Holder, position: Int) {
//
//    }
//
//    override fun getItemCount(): Int {
//        return recentList.size
//    }
//
//
//
//
//}