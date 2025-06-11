package com.example.usolo.util

import android.content.Context
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.usolo.MainActivity
import com.example.usolo.R

object NotificationUtil {

    private const val CHANNEL_ID = "messages";
    private const val CHANNEL_NAME = "Messages";
    private var id = 0;

    fun showNotification(context: Context, title: String, message: String) {
        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notifyIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val notifyPendingIntent = PendingIntent.getActivity(
            context, 0, notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        manager.createNotificationChannel(
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
        )

        manager.notify(
            id++,
            NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentText(message)
                .setContentTitle(title)
                .setContentIntent(notifyPendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build()
        )
    }

}