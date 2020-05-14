package com.moviebrowseapp.presentation.ui.discover

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.moviebrowseapp.domain.model.Movie
import com.moviebrowseapp.domain.repository.DiscoverMovieRepository
import com.moviebrowseapp.domain.repository.SortBy
import com.moviebrowseapp.presentation.base.Event
import com.moviebrowseapp.presentation.ui.discover.model.NavigationEvent
import com.moviebrowseapp.presentation.ui.discover.model.NavigationState
import com.moviebrowseapp.tool.log.extension.logError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*


/**
 * Discover movie view model
 * Fetches data using movie repository
 */
class DiscoverMovieViewModel(private val repository: DiscoverMovieRepository) : ViewModel() {

    data class ViewState(val showBlockingLoading: Boolean = false,
                         val showBottomLoading: Boolean = false,
                         val showContent: Boolean = false,
                         val errorMsg: String? = null,
                         val content: List<Movie>? = null)

    private val viewStateLiveData: MutableLiveData<ViewState> = MutableLiveData()
    private val navigationLiveData: MutableLiveData<NavigationEvent> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()

    private var currPage: Int = 1
    private var isLoading: Boolean = false
    private var canLoadMore: Boolean = true
    private var dateFilter: Date? = null

    private var loadedContent: List<Movie> = emptyList()


    init {
        requestLoad()
    }

    fun observeViewState(): LiveData<ViewState> = viewStateLiveData

    fun observeNavigation(): LiveData<NavigationEvent> = navigationLiveData


    /**
     * Loads next page whenever page is scrolled and reached threshold
     */
    fun onScrolled() {
        if (!isLoading && canLoadMore) {
            currPage++
            requestLoad()
        }
    }

    /**
     * User clicks 'select date' button
     */
    fun onDateFilterClick() {
        changeNavigationState(NavigationState.Calendar)
    }

    /**
     * Date is selected
     */
    fun onDateSelected(date: Date) {
        dateFilter = date

        isLoading = false
        currPage = 1
        requestLoad()
    }

    fun onMovieClick(movie: Movie) {
        changeNavigationState(NavigationState.MovieDetails(movie))
    }

    private fun requestLoad() {
        if (!isLoading) {
            isLoading = true
            changeViewState(ViewState(showBlockingLoading = currPage == 1, showBottomLoading = currPage > 1, showContent = currPage > 1))

            compositeDisposable.add(repository.getMovies(currPage, SortBy.ReleaseDate(true), dateFilter)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::handleContent, ::handleError))
        }
    }

    private fun handleContent(content: List<Movie>) {
        loadedContent = if (currPage == 1) content else loadedContent.plus(content)

        isLoading = false
        canLoadMore = content.isNotEmpty()

        changeViewState(ViewState(showContent = true, content = loadedContent))
    }

    /**
     * Reacts to errors by emitting the proper view state.
     * For the sake of this test assignment the error msg is hardcoded
     */
    private fun handleError(error: Throwable) {
        logError(error)
        changeViewState(ViewState(errorMsg = "There was an error loading content"))

        isLoading = false
    }

    private fun changeViewState(newState: ViewState) {
        viewStateLiveData.postValue(newState)
    }

    private fun changeNavigationState(navigationState: NavigationState) {
        navigationLiveData.postValue(Event(navigationState))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}