package com.moviebrowseapp.domain.network

import android.widget.ImageView


/**
 * Generic interface for image loaders
 */
interface ImageLoader {
    fun displayAsync(url: String, receiver: ImageView)
}