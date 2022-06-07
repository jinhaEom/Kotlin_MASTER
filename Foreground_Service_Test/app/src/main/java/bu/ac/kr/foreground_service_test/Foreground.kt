package bu.ac.kr.foreground_service_test

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class Foreground : Service() {

    //서비스가 사용할 CHANNEL_ID
    val CHANNEL_ID = "foregroundChannel"

    override fun onBind(intent: Intent): IBinder {
        return Binder()
    }
    fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // 오레오 버전 이상부터 noti 이용시 채널 생성
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,"Foreground Service Channel",NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()

        val notification : Notification = NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle("Service")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .build()
        startForeground(1,notification)

        return super.onStartCommand(intent, flags, startId)
    }

}