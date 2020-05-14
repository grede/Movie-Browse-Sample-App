package com.moviebrowseapp.presentation.ui.discover

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.moviebrowseapp.domain.model.Movie
import com.moviebrowseapp.presentation.R


/**
 * Simple movie list adapter
 * Makes use of ListAdapter to handle & calculate content changes automatically
 */
class DiscoverMovieAdapter(private val onClickListener: (Movie) -> Unit)
    : ListAdapter<Movie, DiscoverMovieAdapter.ViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(parent: ViewHolder, position: Int) {
        val item = getItem(position)

        parent.titleView.text = item.title
        parent.itemView.setOnClickListener { onClickListener(item) }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView: TextView = itemView.findViewById(R.id.movie_title)
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(first: Movie, second: Movie): Boolean {
            return first.id == second.id
        }

        override fun areContentsTheSame(first: Movie, second: Movie): Boolean {
            return first == second
        }
    }
}