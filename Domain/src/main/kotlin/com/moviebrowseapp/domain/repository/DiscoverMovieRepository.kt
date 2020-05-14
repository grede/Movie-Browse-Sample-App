package com.moviebrowseapp.domain.repository

import com.moviebrowseapp.domain.model.Movie
import io.reactivex.Single
import java.util.*


interface DiscoverMovieRepository {
    fun getMovies(page: Int, sortBy: SortBy, releasedBefore: Date? = null): Single<List<Movie>>
}

sealed class SortBy(private val value: String, val desc: Boolean) {
    class ReleaseDate(desc: Boolean) : SortBy("release_date", desc)

    fun asParameter(): String = "$value.${if (desc) "desc" else "asc"}"
}