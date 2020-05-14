package com.moviebrowseapp.presentation.ui.discover

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.moviebrowseapp.presentation.R
import com.moviebrowseapp.presentation.base.extension.*
import com.moviebrowseapp.presentation.ui.Navigator
import com.moviebrowseapp.presentation.ui.discover.model.NavigationEvent
import com.moviebrowseapp.presentation.ui.discover.model.NavigationState
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


/**
 * Discover Movie screen
 */
class DiscoverMovieActivity : AppCompatActivity(), KodeinAware {

    private companion object {
        private const val SCROLL_THRESHOLD = 3
    }

    override val kodein: Kodein by closestKodein()

    private val viewModel: DiscoverMovieViewModel by viewModel()

    private val navigator: Navigator by instance<Navigator>()

    private val loadingIndicatorView: View by lazyViewById(R.id.loading_indicator)
    private val smallLoadingIndicatorView: View by lazyViewById(R.id.small_loading_indicator)
    private val recyclerView: RecyclerView by lazyViewById(R.id.recycler_view)

    private val movieAdapter: DiscoverMovieAdapter by lazy { DiscoverMovieAdapter(viewModel::onMovieClick) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover_movies)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = movieAdapter
        recyclerView.observeScroll(SCROLL_THRESHOLD, viewModel::onScrolled)

        viewModel.observeViewState().observeWith(this, ::renderViewState)
        viewModel.observeNavigation().observeWith(this, ::navigate)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.discover_movie_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.select_release_date) {
            viewModel.onDateFilterClick()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * Render view state changes
     *
     * TODO: Error state could be handled in a better way
     */
    private fun renderViewState(viewState: DiscoverMovieViewModel.ViewState) {
        loadingIndicatorView.visibleOrGone(viewState.showBlockingLoading)
        smallLoadingIndicatorView.visibleOrGone(viewState.showBottomLoading)
        recyclerView.visibleOrGone(viewState.showContent)

        viewState.errorMsg?.let { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        viewState.content?.let { movieAdapter.submitList(it) }
    }

    /**
     * React to navigation events emitted by view model
     */
    private fun navigate(event: NavigationEvent) {

        when (val state = event.poll()) {
            is NavigationState.Calendar -> navigator.navigateToCalendar(context = this, callback = viewModel::onDateSelected)
            is NavigationState.MovieDetails -> navigator.navigateToMovieDetails(state.movie, this)
        }
    }
}