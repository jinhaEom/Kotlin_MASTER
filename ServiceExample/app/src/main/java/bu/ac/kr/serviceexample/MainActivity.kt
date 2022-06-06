package bu.ac.kr.serviceexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    // view: View를 사용하면 클릭리스너 없이도 레이아웃파일에서 메서드 접근 가능
    fun serviceStart(view: View){
        val intent = Intent(this, MyService::class.java)
        intent.action = MyService.ACTION_START
        startService(intent)
    }
    fun serviceStop(view: View){
        val intent = Intent(this, MyService::class.java)
        stopService(intent)
    }


}