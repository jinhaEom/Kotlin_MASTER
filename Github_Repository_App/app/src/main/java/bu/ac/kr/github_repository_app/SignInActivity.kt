package bu.ac.kr.github_repository_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isGone
import bu.ac.kr.github_repository_app.databinding.ActivitySignInBinding
import bu.ac.kr.github_repository_app.utility.AuthTokenProvider
import bu.ac.kr.github_repository_app.utility.RetrofitUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class SignInActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var binding: ActivitySignInBinding

    private val authTokenProvider by lazy{ AuthTokenProvider(this)}

    val job : Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(checkAuthCodeExist()){
            launchMainActivity()
        }else {
            initViews()

        }


    }

    private fun launchMainActivity()  {
        startActivity(Intent(this,MainActivity::class.java).apply{
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }
    private fun checkAuthCodeExist() : Boolean = authTokenProvider.token.isNullOrEmpty().not()


    private fun initViews() = with(binding) {
        loginButton.setOnClickListener {
            loginGithub()
        }
    }

    //todo https:///github.com/login/oiauth/authorize?cliend_id=asdfasdf
    private fun loginGithub() {
        val loginUri = Uri.Builder().scheme("https").authority("github.com")
            .appendPath("login")
            .appendPath("oauth")
            .appendPath("authorize")
            .appendQueryParameter("client_id", BuildConfig.GITHUB_CLIENT_ID)
            .build()

        CustomTabsIntent.Builder().build().also {
            it.launchUrl(this, loginUri)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.data?.getQueryParameter("code")?.let {
            launch(coroutineContext){
                showProgress()
                val getAccessTokenJob = getAccessToken(it)
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
    private suspend fun dismissProgress() = withContext(coroutineContext){
        with(binding){
            loginButton.isGone = false
            progressBar.isGone = true
            progressTextView.isGone = true
        }
    }
    private suspend fun getAccessToken(code : String) = withContext(Dispatchers.IO) {
        val response = RetrofitUtil.authApiService.getAccessToken(
            clientId = BuildConfig.GITHUB_CLIENT_ID,
            clientSecret = BuildConfig.GITHUB_CLIENT_SECRET,
            code = code
        )
        if (response.isSuccessful) {
            val accessToken = response.body()?.accessToken ?: ""
            Log.e("accessToken", accessToken)
            if(accessToken.isNotEmpty()){
                authTokenProvider.updateToken(accessToken)
            }else{
                Toast.makeText(this@SignInActivity,"access 토큰이 존재하지 않습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
