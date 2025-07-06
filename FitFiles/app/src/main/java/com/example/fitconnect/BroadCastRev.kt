package com.example.fitconnect

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

//
//class TaskNotificationReceiver : BroadcastReceiver() {
//    override fun onReceive(context: Context, intent: Intent?) {
//        val taskId = intent?.getIntExtra("taskId", 0) ?: 0
//        val taskName = intent?.getStringExtra("taskName") ?: "Task"
//        NotificationUtils.showNotification(context, taskId, taskName)
//    }
//}

class TaskNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("TaskNotificationReceiver", "Task notification received: ${intent?.action}")

        val taskId = intent?.getIntExtra("taskId", 0) ?: 0
        val taskName = intent?.getStringExtra("taskName") ?: "Task"
        Log.d("TaskNotificationReceiver", "Showing notification for task: $taskId - $taskName")

        NotificationUtils.showNotification(context, taskId, taskName)
    }
}