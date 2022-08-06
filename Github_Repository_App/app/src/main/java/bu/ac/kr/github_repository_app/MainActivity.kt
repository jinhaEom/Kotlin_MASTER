package bu.ac.kr.github_repository_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bu.ac.kr.github_repository_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}