package com.moviebrowseapp.tool.log.extension

import com.moviebrowseapp.tool.log.AppLogger


val Any.tag: String
    get() = this.javaClass.simpleName

/**
 * Custom tags are needed in cases when extension tag doesn't correspond to the actual class (for instance call from
 * companion object)
 */
fun Any.logDebug(msg: String, customTag: String? = null) {
    AppLogger.instance.logDebug(customTag ?: tag, msg)
}

fun Any.logWarning(msg: String, customTag: String? = null) {
    AppLogger.instance.logWarning(customTag ?: tag, msg)
}

fun Any.logError(msg: String, customTag: String? = null) {
    AppLogger.instance.logError(customTag ?: tag, msg)
}

fun Any.logError(error: Throwable, customTag: String? = null) {
    AppLogger.instance.logError(customTag ?: tag, error)
}