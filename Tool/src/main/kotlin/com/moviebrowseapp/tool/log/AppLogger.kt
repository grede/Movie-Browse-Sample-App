package com.moviebrowseapp.tool.log

import com.moviebrowseapp.tool.log.base.CompositeLogger
import com.moviebrowseapp.tool.log.base.Logger


/**
 * Simple holder of a logger object, so it can be accessed from anywhere statically without having to pass dependencies
 */
class AppLogger private constructor() {

    companion object {
        val instance: Logger = CompositeLogger()

        fun registerLoggers(vararg loggers: Logger) {
            (instance as CompositeLogger).registerLoggers(loggers)
        }
    }
}