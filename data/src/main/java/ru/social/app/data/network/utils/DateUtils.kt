package ru.social.app.data.network.utils

import android.text.format.DateUtils
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

const val POST_DATE_FORMAT = "dd MMMM HH:mm"
const val BIRTHDAY_DATE_FORMAT = "dd MMMM yyyy"
const val BIRTHDAY_DATE_INPUT_FORMAT = "dd.MM.yyyy"
const val HOUR_FORMAT = "HH:mm"

fun Timestamp.parseDate(): String {
    val date = this.toDate()
    return when {
        DateUtils.isToday(date.time) ->
            "Today at ${SimpleDateFormat(HOUR_FORMAT, Locale.getDefault()).format(this.toDate())}"
        DateUtils.isToday(date.time + DateUtils.DAY_IN_MILLIS) ->
            "Yesterday at ${SimpleDateFormat(HOUR_FORMAT, Locale.getDefault()).format(this.toDate())}"
        else ->
            SimpleDateFormat(POST_DATE_FORMAT, Locale.getDefault()).format(this.toDate())
    }
}

fun Timestamp.parseBirthdayDate(): String =
    SimpleDateFormat(BIRTHDAY_DATE_FORMAT, Locale.getDefault()).format(this.toDate())

fun String.birthdayInputToTimestamp(): Timestamp? =
    SimpleDateFormat(BIRTHDAY_DATE_INPUT_FORMAT).parse(this)?.let { Timestamp(it) }

fun Timestamp.calculateAge(): Int {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val year = Calendar.getInstance()
        .apply { time = this@calculateAge.toDate() }
        .get(Calendar.YEAR)
    return currentYear - year
}