package com.moviebrowseapp

import android.app.Application
import android.content.Context
import com.moviebrowseapp.di.networkModule
import com.moviebrowseapp.di.repositoryModule
import com.moviebrowseapp.presentation.di.presentationModule
import org.kodein.di.DKodein
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider


class MovieBrowseApp : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        bind<Context>() with provider { this@MovieBrowseApp }
        bind<Application>() with provider { this@MovieBrowseApp }
        bind<DKodein>() with provider { direct }

        import(presentationModule)
        import(networkModule)
        import(repositoryModule)
    }
}