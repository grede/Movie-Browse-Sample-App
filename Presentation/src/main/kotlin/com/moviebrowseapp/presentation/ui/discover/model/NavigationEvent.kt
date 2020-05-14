package com.moviebrowseapp.presentation.ui.discover.model

import com.moviebrowseapp.domain.model.Movie
import com.moviebrowseapp.presentation.base.Event


sealed class NavigationState {
    object Calendar : NavigationState()
    data class MovieDetails(val movie: Movie) : NavigationState()
}

typealias NavigationEvent = Event<NavigationState>