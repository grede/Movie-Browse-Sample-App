package com.moviebrowseapp.data.network

import android.widget.ImageView
import com.moviebrowseapp.domain.network.ImageLoader
import com.moviebrowseapp.tool.log.extension.logDebug
import com.squareup.picasso.Picasso


/**
 * Simple ImageLoader implementation based on Picasso
 *
 * @see ImageLoader
 */
class PicassoImageLoader(private val picasso: Picasso) : ImageLoader {

    override fun displayAsync(url: String, receiver: ImageView) {
        logDebug("Loading image: $url")
        picasso.load(url).into(receiver)
    }
}