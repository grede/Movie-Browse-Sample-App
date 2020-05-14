package com.moviebrowseapp.presentation.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import com.moviebrowseapp.domain.model.Movie
import com.moviebrowseapp.presentation.ui.details.DiscoverMovieDetailsActivity
import java.util.*


/**
 * Simple navigator class to extract navigation logic from UI screen
 */
class Navigator {

    fun navigateToCalendar(date: Date? = null, context: Context, callback: (Date) -> Unit) {
        val c = Calendar.getInstance()

        if (date != null) {
            c.time = date
        }

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(context, toPickerCallback(callback), year, month, day).show()
    }

    fun navigateToMovieDetails(movie: Movie, activity: Activity) {
        activity.startActivity(DiscoverMovieDetailsActivity.newIntent(movie, activity))
    }

    private fun toPickerCallback(callback: (Date) -> Unit): (DatePicker, Int, Int, Int) -> Unit {
        val c = Calendar.getInstance()

        return { _, yy, mm, dd ->
            callback(Calendar.getInstance()
                    .apply { set(Calendar.YEAR, yy) }
                    .apply { set(Calendar.MONTH, mm) }
                    .apply { set(Calendar.DAY_OF_MONTH, dd) }
                    .apply { set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY)) }
                    .apply { set(Calendar.MINUTE, c.get(Calendar.MINUTE)) }.time)
        }
    }
}