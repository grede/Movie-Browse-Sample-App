package com.moviebrowseapp.di

import com.moviebrowseapp.BuildConfig
import com.moviebrowseapp.data.repository.DiscoverMovieRepositoryImpl
import com.moviebrowseapp.domain.repository.DiscoverMovieRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


/**
 * Module that holds repositories
 */
var repositoryModule = Kodein.Module(name = "repositoryModule") {
    bind<DiscoverMovieRepository>() with singleton { DiscoverMovieRepositoryImpl(instance(), BuildConfig.MOVIE_DB_API_KEY) }
}