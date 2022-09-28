package bu.ac.kr.livedataex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import bu.ac.kr.livedataex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var liveText : MutableLiveData<String> = MutableLiveData()
    private var count = 0  // button 누르면 증가될 숫자
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        liveText.observe(this, Observer {
            binding.testText.text= it
        })
        binding.btnChange.setOnClickListener {
            liveText.value = "Hello World! Level Up : ${++count}"
        }
    }
}