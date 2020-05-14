package com.moviebrowseapp.presentation.base.extension

import android.arch.lifecycle.*
import android.support.v4.app.FragmentActivity
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.instance


fun <D : Any, T : LiveData<D>> T.observeWith(owner: LifecycleOwner, receiver: (D) -> Unit) {
    observe(owner, Observer<D> {
        if (it != null) {
            receiver(it)
        }
    })
}

inline fun <reified VM : ViewModel, T> T.viewModel(): Lazy<VM> where T : KodeinAware, T : FragmentActivity {
    return lazy { ViewModelProviders.of(this, direct.instance()).get(VM::class.java) }
}

