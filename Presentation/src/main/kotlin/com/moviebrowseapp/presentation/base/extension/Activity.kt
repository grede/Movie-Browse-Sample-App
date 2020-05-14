package com.moviebrowseapp.presentation.base.extension

import android.app.Activity
import android.support.annotation.IdRes
import android.view.View

inline fun <reified V: View> Activity.lazyViewById(@IdRes viewId: Int): Lazy<V> {
    return lazy { findViewById<V>(viewId) }
}