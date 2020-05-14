package com.moviebrowseapp.presentation.base.extension

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View


fun View.visibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun RecyclerView.observeScroll(threshold: Int, onScrolled: () -> Unit) {
    val scrollListener = RecyclerViewScrollListener(threshold, layoutManager, adapter, onScrolled)

    adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {

        override fun onChanged() {
            super.onChanged()
            scrollListener.isScrollDisabled = false
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            scrollListener.isScrollDisabled = false
        }
    })

    addOnScrollListener(scrollListener)
}

class RecyclerViewScrollListener(private val threshold: Int,
                                 private val layoutManager: RecyclerView.LayoutManager?,
                                 private val adapter: RecyclerView.Adapter<*>?,
                                 private val onScrolled: () -> Unit) : RecyclerView.OnScrollListener() {

    var isScrollDisabled: Boolean = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (layoutManager == null || layoutManager !is LinearLayoutManager || isScrollDisabled) {
            return
        }

        val total = adapter?.itemCount ?: 0
        val diff = total - layoutManager.findLastVisibleItemPosition()

        if (total > 0 && diff <= threshold) {
            isScrollDisabled = true
            onScrolled()
        }
    }
}