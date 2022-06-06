package bu.ac.kr.serviceexample

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        Log.d("StartedService","action=$action")

        return super.onStartCommand(intent, flags, startId)

    }
    companion object{
        val ACTION_START = "bu.ac.kr.serviceexample.START"
        val ACTION_RUN = "bu.ac.kr.serviceexample.RUN"
        val ACTION_STOP = "bu.ac.kr.serviceexample.STOP"
    }
}