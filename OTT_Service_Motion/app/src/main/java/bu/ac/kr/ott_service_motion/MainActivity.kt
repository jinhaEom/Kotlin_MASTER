package bu.ac.kr.ott_service_motion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bu.ac.kr.ott_service_motion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)




    }
}