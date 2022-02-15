package bu.ac.kr.simple_chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bu.ac.kr.simple_chat.Chat.ChatFragment
import bu.ac.kr.simple_chat.databinding.ActivityMainBinding

private lateinit var binding : ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportFragmentManager.beginTransaction()
            .replace(R.id.layout_frame,LoginFragment())
            .commit()

        fun replaceFragment(bundle: Bundle){
            val destination = ChatFragment()
            destination.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.layout_frame,destination)
                .commit()
        }
    }


}