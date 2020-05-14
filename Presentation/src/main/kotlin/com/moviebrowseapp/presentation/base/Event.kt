package com.moviebrowseapp.presentation.base


/**
 * One time event implementation
 * Useful for usage with view model when emitted change should only be processed once (like navigation even)
 */
class Event<T>(private val data: T) {

    var isConsumed: Boolean = false
        private set

    fun poll(): T? {
        if (!isConsumed) {
            isConsumed = true
            return data
        }

        return null
    }

    fun peek(): T {
        return data
    }
}