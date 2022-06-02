package bu.ac.kr.treat_repo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bu.ac.kr.treat_repo.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
    }
}