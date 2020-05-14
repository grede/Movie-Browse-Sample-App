package com.moviebrowseapp.tool.log.base

import java.util.concurrent.CopyOnWriteArraySet


/**
 * A composite logger, which just delegates log calls to all registered loggers
 */
class CompositeLogger : Logger {

    private val loggers: MutableSet<Logger> = CopyOnWriteArraySet()

    fun registerLoggers(loggers: Array<out Logger>) {
        this.loggers.addAll(loggers)
    }

    override fun logDebug(tag: String, msg: String) {
        loggers.forEach {
            if (it.isEnabled()) {
                it.logDebug(tag, msg)
            }
        }
    }

    override fun logWarning(tag: String, msg: String) {
        loggers.forEach {
            if (it.isEnabled()) {
                it.logWarning(tag, msg)
            }
        }
    }

    override fun logError(tag: String, msg: String) {
        loggers.forEach {
            if (it.isEnabled()) {
                it.logError(tag, msg)
            }
        }
    }

    override fun logError(tag: String, error: Throwable) {
        loggers.forEach {
            if (it.isEnabled()) {
                it.logError(tag, error)
            }
        }
    }
}