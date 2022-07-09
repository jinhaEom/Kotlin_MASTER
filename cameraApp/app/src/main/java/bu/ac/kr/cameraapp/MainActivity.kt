package bu.ac.kr.cameraapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bu.ac.kr.cameraapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}