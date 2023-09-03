package com.practice.intent_practice

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                // id는 notification 객체에서 정의한 channelId와 동일해야함
                "running_channel",
                // 유저가 app setting에서 보게 될 이름(앱에서 보내는 것이 어떠한 종류의 notification인지)
                "Running Notifications",
                // importance of notification
                NotificationManager.IMPORTANCE_HIGH
            )

            // get system service, as showing a notification is obviously your app isn't allowed to do
            // so android system provides a service for that
            // a service directly comes from the android os
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}