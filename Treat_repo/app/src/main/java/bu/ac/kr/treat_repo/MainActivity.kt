package bu.ac.kr.treat_repo

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.viewbinding.BuildConfig
import bu.ac.kr.treat_repo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }
    private fun initViews() = with(binding){
        loginButton.setOnClickListener {
            loginGithub()
        }
    }
    private fun loginGithub(){
        val loginUri = Uri.Builder().scheme("https").authority("github.com")
            .appendPath("login")
            .appendPath("oauth")
            .appendPath("authorize")
            .appendQueryParameter("client_id", bu.ac.kr.treat_repo.BuildConfig.GITHUB_CLIENT_ID)
            .build()

        CustomTabsIntent.Builder().build().also {
            it.launchUrl(this,loginUri)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.data?.getQueryParameter("code")?.let{

        }
    }
}