package bu.ac.kr.subway_arriveinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bu.ac.kr.subway_arriveinfo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy{ ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}