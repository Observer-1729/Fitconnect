package com.example.fitconnect

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ClearTaskReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        // Check that this is really the intended clear task action
        if (intent?.action != "com.example.fitconnect.CLEAR_TASKS") {
            Log.d("ClearTaskReceiver", "Ignoring unintended intent: ${intent?.action}")
            return
        }

        Log.d("ClearTaskReceiver", "Starting daily task clearing")

        // Get database access directly instead of via ViewModel
        val db = AppDatabase.getDatabase(context.applicationContext)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val tasks = db.taskDao().getAllTasks().first()
                Log.d("ClearTaskReceiver", "Found ${tasks.size} tasks to clear")

                // Cancel all notifications
                tasks.forEach { task ->
                    val notificationIntent = Intent(context, TaskNotificationReceiver::class.java)
                    val pendingIntent = PendingIntent.getBroadcast(
                        context,
                        task.id,
                        notificationIntent,
                        PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
                    )
                    if (pendingIntent != null) {
                        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                        alarmManager.cancel(pendingIntent)
                        pendingIntent.cancel()
                    }
                }

                // Clear all tasks
                db.taskDao().clearAll()
                Log.d("ClearTaskReceiver", "Tasks cleared successfully")
            } catch (e: Exception) {
                Log.e("ClearTaskReceiver", "Error clearing tasks", e)
            }
        }
    }
}