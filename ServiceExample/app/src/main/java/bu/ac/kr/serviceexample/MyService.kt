package bu.ac.kr.serviceexample

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {

        return binder
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
    override fun onDestroy() {
        Log.d("Service", "서비스가 종료되었습니다.")
        super.onDestroy()
    }
    //Bound Service는 Started Service와 달리 Activity에서 서비스의 Method를 직접 호출해 사용 가능

    inner class MyBinder: Binder(){
        fun getService(): MyService{
            return this@MyService
        }
    }
    val binder = MyBinder()
    fun serviceMessage(): String{
        return "Hello Service"
    }
}