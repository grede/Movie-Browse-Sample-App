package com.moviebrowseapp.presentation.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.moviebrowseapp.domain.model.Movie
import com.moviebrowseapp.domain.network.ImageLoader
import com.moviebrowseapp.presentation.R
import com.moviebrowseapp.presentation.base.extension.lazyViewById
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


/**
 * Movie Details screen
 * View Model is omitted for the sake of simplicity
 */
class DiscoverMovieDetailsActivity : AppCompatActivity(), KodeinAware {

    companion object {
        private const val EXTRA_MOVIE = "extra_movie"

        fun newIntent(movie: Movie, context: Context): Intent {
            return Intent(context, DiscoverMovieDetailsActivity::class.java)
                    .putExtra(EXTRA_MOVIE, movie)
        }
    }

    override val kodein: Kodein by closestKodein()

    private val imageLoader: ImageLoader by instance<ImageLoader>()
    private val movie: Movie by lazy { intent.getParcelableExtra<Movie>(EXTRA_MOVIE) }

    private val movieDescriptionView: TextView by lazyViewById(R.id.movie_description)
    private val movieRating: TextView by lazyViewById(R.id.movie_rating)
    private val movieVoteCount: TextView by lazyViewById(R.id.movie_vote_count)
    private val moviePosterView: ImageView by lazyViewById(R.id.movie_poster)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        setMovieDescription(movie.overview)
        setMovieRating(movie.voteAvg)
        setMovieVoteCount(movie.voteCount)
        setMoviePoster(movie.posterUrl())
    }

    private fun setMovieRating(rating: Double) {
        movieRating.text = getString(R.string.movie_rating, rating.toString())
    }

    private fun setMovieVoteCount(count: Int) {
        movieVoteCount.text = getString(R.string.movie_vote_count, count)
    }

    private fun setMoviePoster(posterUrl: String?) {
        if (posterUrl != null) {
            imageLoader.displayAsync(posterUrl, moviePosterView)
        } else {
            moviePosterView.visibility = View.GONE
        }
    }

    private fun setMovieDescription(description: String) {
        if (description.isEmpty()) {
            movieDescriptionView.visibility = View.GONE
        } else {
            movieDescriptionView.text = getString(R.string.movie_description, description)
        }
    }
}