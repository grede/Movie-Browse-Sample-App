package com.moviebrowseapp.tool.log

import android.util.Log
import com.moviebrowseapp.tool.log.base.Logger


class LogcatLogger : Logger {

    override fun logDebug(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    override fun logWarning(tag: String, msg: String) {
        Log.w(tag, msg)
    }

    override fun logError(tag: String, msg: String) {
        Log.e(tag, msg)
    }

    override fun logError(tag: String, error: Throwable) {
        Log.e(tag, error.message, error)
    }
}