package com.dev.firebasecloudmessage

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.net.URL
import android.R
import android.app.NotificationChannel
import android.content.Context
import android.os.Build


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        val notification = remoteMessage?.notification as RemoteMessage.Notification
        val data = remoteMessage?.data as Map<String,String>

        sendNotification(notification,data)
    }

    private fun sendNotification(notification: RemoteMessage.Notification,data :Map<String,String>){

        val icon = BitmapFactory.decodeResource(resources,R.mipmap.sym_def_app_icon)

        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)

        val notificationBuilder = NotificationCompat.Builder(this, "channel_id")
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(pendingIntent)
            .setContentInfo(notification.title)
            .setLargeIcon(icon)
            .setColor(Color.RED)
            .setLights(Color.RED, 1000, 300)
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setSmallIcon(R.mipmap.sym_def_app_icon)


        try {
            val pictureUrl = data["picture_url"]
            if(pictureUrl != null && pictureUrl != ""){
                val url = URL(pictureUrl)
                val bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream()) as NotificationCompat.Builder
                notificationBuilder.setStyle(NotificationCompat.BigPictureStyle(bigPicture).setSummaryText(notification.body))

            }
        } catch (e: Exception) {
            e.stackTrace
        }

        val notificationManager  = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel("channel_id","channel_name",NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "channel description"
                setShowBadge(true)
                canShowBadge()
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                vibrationPattern = longArrayOf(100,200,300,400,500)
            }.run {
                notificationManager.createNotificationChannel(this)
            }
        }
        notificationManager.notify(0,notificationBuilder.build())
    }
}