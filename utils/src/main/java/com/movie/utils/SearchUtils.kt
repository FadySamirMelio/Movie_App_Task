package com.movie.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import com.movie.utils.R

object SearchUtils {
    fun initSearch(
        searchView: SearchView,
        isFastSearch: Boolean = false,
        title: String,
        search: (String) -> Unit,
    ) {

        searchView.queryHint = title
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    search.invoke(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (isFastSearch)
                        search.invoke(it)
                }
                return true
            }
        })
    }

    fun closeSearch(searchView: SearchView, close: () -> Unit) {
        searchView.findViewById<AppCompatImageView>(R.id.search_close_btn).setOnClickListener {
            searchView.setQuery(null, false)
            close.invoke()
            searchView.clearFocus()
        }
    }
}