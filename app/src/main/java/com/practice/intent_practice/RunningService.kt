package com.practice.intent_practice

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

class RunningService: Service() {

    // define what kind of service it is

    // onBind
    // receives an intent, returns an IBinder

    // IBinder

    override fun onBind(intent: Intent?): IBinder? {
        // to create a "Bound Service"
        // with bound service, you can make one active instance and multiple components connect to that single instance
        // can have a stream for communication to communicate with the service
        // can receive events or callbacks from the service
        // if there is a use case of multiple apps needed to connect to one single service, use service class

        // nothing can actually bind to that service, the service doesn't do anything
        return null
    }

    // commands android components can send to the service

    // when to launch the service
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // is launched whenever one of android components sends an intent to this service
        // intent로부터 받은 action에 따라 다른 이벤트를 처리 가능

        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
            // stopSelf() : stop the service instance...
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        // for making this 'foreground service',
        // it needs to come with a persistent "notification"

        // 필요한 함수 : startForeground
        // 매개변수
        // 1. id : corresponds to the specific notification
        // if you want to update the notification, just call startForeground() with the same id with the updated notification
        // ** 주의 ** 0으로 설정하면 동작하지 않는다. 최소 1
        // 2. notification :

        // channelId : corresponds to notification channels on Android which we need to create if we want to show a notification
        // category for specific set of notifications
        // notification의 카테고리 별로 set을 다르게 지정 가능. 같은 애플리케이션이라도 관련된 알림들끼리 묶어서 표시할 수 있는 것은 이 때문이다. 그룹에 따라 notification 볼 수 있는 여부를 토글 가능
        val notification = NotificationCompat.Builder( this, "running_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // icon with a notification inside the "status bar"
            .setContentTitle("Run is Active")
            .setContentText("Elapsed time : 00")
            .build()

        startForeground(1, notification)

        // 여기까지 하면, 서비스는 동작하지 않는다.
        // 인텐트로 다른 컴포넌트(액티비티 등)과 소통해야 동작할 수 있기 때문
        // 즉 인텐트가 서비스 클래스로 도착해야 동작할 수 있음
        // 액티비티 등에서 인텐트를 서비스로 전송해야 서비스를 시작 가능

        // 또한, notification에서 정의한 channelId가 있기 때문에 추가적인 조치가 필요
        // 애플리케이션이 '동작할 때' notification이 만들어져야 함 => application 클래스를 정의하여
    }

    // when to stop the service


    enum class Actions {
        START, STOP;
    }
}

