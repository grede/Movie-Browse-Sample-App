package com.moviebrowseapp.presentation.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import org.kodein.di.DKodein
import org.kodein.di.generic.instanceOrNull


/**
 * This a simple factory responsible for ViewModel retrieval
 *
 * It relies directly on Kodein DI and tries to get an instance of ViewModel by its tag
 * If instance of ViewModel can't be retrieved from DI, standard instantiation mechanism is used instead
 */
class ViewModelFactory(private val injector: DKodein) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return injector.instanceOrNull<ViewModel>(tag = modelClass.simpleName) as T?
                ?: modelClass.newInstance()
    }
}