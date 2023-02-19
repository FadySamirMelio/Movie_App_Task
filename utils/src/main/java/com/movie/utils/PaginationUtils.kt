package com.movie.utils

import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


object PaginationUtils {

    fun addLoadMoreListener(recyclerView: RecyclerView?, totalPages: Int, listener: (Int) -> Unit) {
        if (recyclerView == null || listener == null) return
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView
                    .layoutManager as LinearLayoutManager?
                val totalItemCount = layoutManager!!.itemCount
                val lastVisible = layoutManager.findLastVisibleItemPosition()
                val endHasBeenReached = lastVisible + 2 >= totalItemCount
                if (totalItemCount > 0 && endHasBeenReached) {
                    listener.invoke(totalPages)
                }
            }
        })
    }

}
