package com.moviebrowseapp.presentation.ui.discover

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.moviebrowseapp.domain.model.Movie
import com.moviebrowseapp.domain.repository.DiscoverMovieRepository
import com.moviebrowseapp.domain.repository.SortBy
import com.moviebrowseapp.presentation.ui.discover.DiscoverMovieViewModel.ViewState
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.robolectric.RobolectricTestRunner
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.TimeUnit


@RunWith(RobolectricTestRunner::class)
class DiscoverMovieViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val mockRepository: DiscoverMovieRepository = mock()


    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        whenever(mockRepository.getMovies(anyInt(), anyOrNull(), anyOrNull())).thenReturn(Single.just(emptyList()))
    }

    @After
    fun tearDown() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }

    @Test
    fun verify_view_model_create_emits_load_state() {
        whenever(mockRepository.getMovies(anyInt(), anyOrNull(), anyOrNull())).thenReturn(Single.create {})

        val viewModel = DiscoverMovieViewModel(mockRepository)
        val mockViewStateObserver: Observer<ViewState> = mock()
        val viewStateCaptor = argumentCaptor<ViewState>()

        viewModel.observeViewState().observeForever(mockViewStateObserver)

        then(mockViewStateObserver).should(times(1)).onChanged(viewStateCaptor.capture())

        assertTrue(viewStateCaptor.firstValue.showBlockingLoading)
        assertFalse(viewStateCaptor.firstValue.showContent)
        assertFalse(viewStateCaptor.firstValue.showBottomLoading)
        assertNull(viewStateCaptor.firstValue.content)
        assertNull(viewStateCaptor.firstValue.errorMsg)
    }

    @Test
    fun verify_view_model_create_fetches_first_page() {
        DiscoverMovieViewModel(mockRepository)
        val sortByCaptor = argumentCaptor<SortBy>()

        then(mockRepository).should(times(1)).getMovies(eq(1), sortByCaptor.capture(), eq(null))
        assertTrue(sortByCaptor.firstValue is SortBy.ReleaseDate)
        assertTrue((sortByCaptor.firstValue as SortBy.ReleaseDate).desc)
    }

    @Test
    fun verify_view_model_observe_emits_data_if_request_succeed() {
        val mockMovie = createTestMovie()
        whenever(mockRepository.getMovies(anyInt(), anyOrNull(), anyOrNull())).thenReturn(Single.just(listOf(mockMovie)))

        val viewModel = DiscoverMovieViewModel(mockRepository)
        val mockViewStateObserver: Observer<ViewState> = mock()
        val viewStateCaptor = argumentCaptor<ViewState>()

        viewModel.observeViewState().observeForever(mockViewStateObserver)
        then(mockViewStateObserver).should(times(1)).onChanged(viewStateCaptor.capture())

        assertFalse(viewStateCaptor.firstValue.showBlockingLoading)
        assertFalse(viewStateCaptor.firstValue.showBottomLoading)
        assertTrue(viewStateCaptor.firstValue.showContent)
        assertNull(viewStateCaptor.firstValue.errorMsg)
        assertNotNull(viewStateCaptor.firstValue.content)
        assertEquals(mockMovie, viewStateCaptor.firstValue.content?.get(0))
    }

    @Test
    fun verify_view_model_observe_emits_error_if_request_fail() {
        val scheduler = TestScheduler()
        RxJavaPlugins.setIoSchedulerHandler { scheduler }

        whenever(mockRepository.getMovies(anyInt(), anyOrNull(), anyOrNull())).thenReturn(Single.error(IllegalStateException()))

        val viewModel = DiscoverMovieViewModel(mockRepository)
        val mockViewStateObserver: Observer<ViewState> = mock()
        val viewStateCaptor = argumentCaptor<ViewState>()

        scheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        viewModel.observeViewState().observeForever(mockViewStateObserver)
        then(mockViewStateObserver).should(times(1)).onChanged(viewStateCaptor.capture())

        assertFalse(viewStateCaptor.firstValue.showBlockingLoading)
        assertFalse(viewStateCaptor.firstValue.showBottomLoading)
        assertFalse(viewStateCaptor.firstValue.showContent)
        assertNull(viewStateCaptor.firstValue.content)
        assertNotNull(viewStateCaptor.firstValue.errorMsg)
    }

    private fun createTestMovie(): Movie {
        return Movie(
                id = 1L,
                title = "test_title",
                overview = "test_overview",
                voteCount = 10,
                voteAvg = 5.0,
                posterPath = "test_poster_path",
                releaseDate = Date()
        )
    }
}