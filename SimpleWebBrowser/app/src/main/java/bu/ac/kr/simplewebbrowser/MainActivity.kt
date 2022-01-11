package bu.ac.kr.simplewebbrowser

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.ContentLoadingProgressBar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {
    private val goHomeButton: ImageButton by lazy {
        findViewById(R.id.goHomeButton)
    }

    private val addressBar: EditText by lazy {
        findViewById(R.id.addressBar)
    }
    private val goBackButton: ImageButton by lazy {
        findViewById(R.id.goBackButton)
    }
    private val goForwardButton: ImageButton by lazy {
        findViewById(R.id.goForwardButton)
    }

    private val webView: WebView by lazy {
        findViewById(R.id.webView)
    }
    private val refreshLayout: SwipeRefreshLayout by lazy {
        findViewById(R.id.refreshLayout)
    }
    private val progressBar: ContentLoadingProgressBar by lazy{
        findViewById(R.id.progressBar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        initViews()
        bindViews()
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews() {
        webView.apply {

            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true  //구글에서 사이드메뉴 사용하지 못하는 문제 해결.
            webChromeClient=WebChromeClient()
            //js를 기본적으로 지원하지 않는 이유는 보안상의 이유 때문.
            loadUrl(DEFAULT_URL)
        }
    }

    private fun bindViews() {

        goHomeButton.setOnClickListener {
            webView.loadUrl(DEFAULT_URL)
        }
        addressBar.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val loadingUrl = v.text.toString()
                if(URLUtil.isNetworkUrl(loadingUrl)){
                    webView.loadUrl(loadingUrl)
                }else{
                    webView.loadUrl("http://$loadingUrl")
                }
                webView.loadUrl(v.text.toString()) //주소창에 text를 가져와서 toString 으로 변환.

            }
            return@setOnEditorActionListener false  //키보드를 내려야하기때문에 false 반환

        }
        goBackButton.setOnClickListener {
            webView.goBack()
        }
        goForwardButton.setOnClickListener {
            webView.goForward()

        }
        refreshLayout.setOnRefreshListener {
            webView.reload()
        }
    }
    inner class WebViewClient: android.webkit.WebViewClient(){

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressBar.show()

        }
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            refreshLayout.isRefreshing = false
            progressBar.hide()
            goBackButton.isEnabled = webView.canGoBack()
            goForwardButton.isEnabled = webView.canGoForward()
            addressBar.setText(url)
        }
    }

    inner class WebChromeClient: android.webkit.WebChromeClient(){
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)

            progressBar.progress = newProgress
        }
    }
    companion object {
        private const val DEFAULT_URL = "http://www.google.com"
    }
}