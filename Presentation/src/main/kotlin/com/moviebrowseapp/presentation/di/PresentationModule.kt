package com.moviebrowseapp.presentation.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.moviebrowseapp.presentation.base.ViewModelFactory
import com.moviebrowseapp.presentation.ui.Navigator
import com.moviebrowseapp.presentation.ui.discover.DiscoverMovieViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


var presentationModule = Kodein.Module(name = "presentationModule") {
    bind<Navigator>() with provider { Navigator() }
    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(instance()) }

    bind<ViewModel>(tag = DiscoverMovieViewModel::class.java.simpleName) with provider {
        DiscoverMovieViewModel(instance())
    }
}