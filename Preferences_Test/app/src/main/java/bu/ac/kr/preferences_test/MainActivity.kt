package bu.ac.kr.preferences_test

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import bu.ac.kr.preferences_test.databinding.ActivityMainBinding

//480p
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val shared = PreferenceManager.getDefaultSharedPreferences(this)
        val switchValue = shared.getBoolean("key_add_shortcut",false)
        val name = shared.getString("key_edit_name","")
        val selected = shared.getString("key_set_item","")
    }
}