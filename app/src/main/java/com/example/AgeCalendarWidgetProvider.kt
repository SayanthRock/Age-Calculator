package com.example

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.time.LocalDate
import java.time.Period
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class AgeCalendarWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {
        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val views = RemoteViews(context.packageName, R.layout.widget_age_calendar)

            val prefs = context.getSharedPreferences("age_prefs", Context.MODE_PRIVATE)
            val dobEpoch = prefs.getLong("saved_dob", LocalDate.of(1990, 1, 1).toEpochDay())
            val dob = LocalDate.ofEpochDay(dobEpoch)
            val today = LocalDate.now()

            val agePeriod = Period.between(dob, today)
            val nowMonth = YearMonth.now()

            // Header info
            val monthYearStr = nowMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy"))
            views.setTextViewText(R.id.widget_month_year, monthYearStr)
            views.setTextViewText(R.id.widget_age_summary, "${agePeriod.years} yrs")

            // Build grid text string for current month
            val firstDayOfMonth = nowMonth.atDay(1)
            val daysInMonth = nowMonth.lengthOfMonth()
            val firstDayOffset = firstDayOfMonth.dayOfWeek.value % 7

            val sb = StringBuilder()
            var currentDay = 1

            // First week line
            for (i in 0 until 7) {
                if (i < firstDayOffset) {
                    sb.append("    ")
                } else {
                    val dayStr = if (currentDay == today.dayOfMonth) "[$currentDay]" else String.format("%2d", currentDay)
                    sb.append(String.format("%3s ", dayStr))
                    currentDay++
                }
            }
            sb.append("\n")

            // Subsequent lines
            while (currentDay <= daysInMonth) {
                for (i in 0 until 7) {
                    if (currentDay <= daysInMonth) {
                        val dayStr = if (currentDay == today.dayOfMonth) "[$currentDay]" else String.format("%2d", currentDay)
                        sb.append(String.format("%3s ", dayStr))
                        currentDay++
                    } else {
                        sb.append("    ")
                    }
                }
                sb.append("\n")
            }

            views.setTextViewText(R.id.widget_grid_content, sb.toString().trimEnd())

            // Pending intent to launch MainActivity
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_root, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
