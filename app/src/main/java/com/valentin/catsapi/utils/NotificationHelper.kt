package com.valentin.catsapi.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.valentin.catsapi.R

object NotificationHelper {
    fun showNotification(context: Context, bitmap: Bitmap, name: String) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Cat Api")
            .setContentText("Image '$name' saved.")
            .setSmallIcon(R.drawable.ic_baseline_save_alt_24)
            .setLargeIcon(bitmap)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(LOAD_NOTIFICATION_ID, builder.build())
        }
    }

    fun createNotificationChannel(context: Context) {
        // api always >= 26
        val descriptionText = "Cat api notifications"
        val channel = NotificationChannel(CHANNEL_ID, NAME, IMPORTANCE).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private const val TAG = "NotificationHelper"
    private const val IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT
    private const val NAME = "Cat api"
    private const val CHANNEL_ID = "cat_api"
    private const val LOAD_NOTIFICATION_ID = 2508
}
