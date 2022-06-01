package bu.ac.kr.treat_repo

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isGone
import androidx.viewbinding.BuildConfig
import bu.ac.kr.treat_repo.databinding.ActivityMainBinding
import bu.ac.kr.treat_repo.utility.AuthTokenProvider
import bu.ac.kr.treat_repo.utility.RetrofitUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() , CoroutineScope {

    private lateinit var binding: ActivityMainBinding

    private val authTokenProvider by lazy { AuthTokenProvider(this)}

    val job:Job = Job()
    override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

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
            launch(coroutineContext) {
                showProgress()
                getAccessToken(it)
                dismissProgress()

            }
        }
    }
    private suspend fun showProgress() = withContext(coroutineContext){
        with(binding){
            loginButton.isGone = true
            progressBar.isGone = false
            progressTextView.isGone = false
        }
    }
    private suspend fun  dismissProgress() = withContext(coroutineContext){
        with(binding){
            loginButton.isGone = false
            progressBar.isGone = true
            progressTextView.isGone = true

        }
    }
    private suspend fun getAccessToken(code:String) = withContext(Dispatchers.IO){
        val response = RetrofitUtil.authApiService.getAccessToken(
            clientId = bu.ac.kr.treat_repo.BuildConfig.GITHUB_CLIENT_ID,
            clientSecret = bu.ac.kr.treat_repo.BuildConfig.GITHUB_CLIENT_SECRET,
            code = code
        )
        if (response.isSuccessful){
            val accessToken = response.body()?.accessToken ?: ""
            if(accessToken.isNotEmpty()){
                authTokenProvider.updateToken(accessToken)
            }else{
                Toast.makeText(this@MainActivity,"accessToken이 존재하지 않습니다.",Toast.LENGTH_SHORT).show()
            }
        }

    }
}