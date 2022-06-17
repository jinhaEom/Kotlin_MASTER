package bu.ac.kr.preferences_test

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bu.ac.kr.preferences_test.databinding.ActivityMainBinding

//480p
class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}