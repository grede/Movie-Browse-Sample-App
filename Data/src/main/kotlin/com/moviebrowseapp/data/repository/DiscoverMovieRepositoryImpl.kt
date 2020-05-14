package com.moviebrowseapp.data.repository

import com.moviebrowseapp.domain.api.MovieDbApi
import com.moviebrowseapp.domain.model.Movie
import com.moviebrowseapp.domain.repository.DiscoverMovieRepository
import com.moviebrowseapp.domain.repository.SortBy
import com.moviebrowseapp.tool.log.extension.logDebug
import io.reactivex.Single
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class DiscoverMovieRepositoryImpl(private val api: MovieDbApi,
                                  private val apiKey: String) : DiscoverMovieRepository {

    private companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd"
    }

    private val dateFormat: DateFormat by lazy { SimpleDateFormat(DATE_FORMAT, Locale.getDefault()) }

    override fun getMovies(page: Int, sortBy: SortBy, releasedBefore: Date?): Single<List<Movie>> {
        return api.discoverMovies(apiKey, page, sortBy.asParameter(), formatDate(releasedBefore))
                .doOnSuccess { logDebug("success: $it") }
                .map { it.results }
    }

    private fun formatDate(date: Date?): String? {
        if (date != null) {
            return dateFormat.format(date)
        }

        return null
    }
}