package bu.ac.kr.foreground_service_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import bu.ac.kr.foreground_service_test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.startButton.setOnClickListener {

            val intent = Intent(this, Foreground::class.java)
            ContextCompat.startForegroundService(this, intent)
        }
        binding.stopButton.setOnClickListener {
            stopService(intent)
        }
    }

}