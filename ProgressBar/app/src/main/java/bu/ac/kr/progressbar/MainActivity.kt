package bu.ac.kr.progressbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import bu.ac.kr.progressbar.databinding.ActivityMainBinding
import kotlin.concurrent.thread

/**
 * 메인 스레드 - 화면에 그림을 그려주는 역할(UI Thread)
 *
 */
class MainActivity : AppCompatActivity() {
    val binding by lazy{ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        thread(start=true){
            Thread.sleep(3000)// 화면에 그림 그리는것 멈춤.
            runOnUiThread{
                showProgress(false)  // showProgress 메서드를 백그라운드에서 호출하기 때문에 메인스레드에서 사용할수 있게 해줌

            }
        }

    }
    fun showProgress(show: Boolean){
        if(show){
            binding.ProgressBar.visibility = View.VISIBLE
        }
        else{
            binding.ProgressBar.visibility = View.GONE
        }
    }

}

