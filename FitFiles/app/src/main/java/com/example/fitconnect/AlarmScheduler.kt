package com.example.fitconnect

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.Calendar


fun setDailyTaskClearAlarm(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, ClearTaskReceiver::class.java).apply {
        action = "com.example.fitconnect.CLEAR_TASKS"
    }

    // Use a different request code - NOT ZERO and not a task ID
    val CLEAR_TASKS_REQUEST_CODE = 987654

    // Cancel any existing alarm first
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        CLEAR_TASKS_REQUEST_CODE,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    alarmManager.cancel(pendingIntent)

    val calendar = Calendar.getInstance().apply {
        // Set time to midnight (12:00 AM)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        add(Calendar.DAY_OF_MONTH, 1) // Set for tomorrow
    }

    // Trigger the alarm at midnight every day
    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )

    Log.d("TaskAlarm", "Daily task clear alarm set for: ${calendar.time}")
}
