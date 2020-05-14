package com.moviebrowseapp.tool.log.base

import com.moviebrowseapp.tool.BuildConfig


/**
 * Generic interface describing a common logger
 */
interface Logger {
    fun isEnabled(): Boolean = BuildConfig.DEBUG

    fun logDebug(tag: String, msg: String) {}
    fun logWarning(tag: String, msg: String) {}
    fun logError(tag: String, msg: String) {}
    fun logError(tag: String, error: Throwable) {}
}