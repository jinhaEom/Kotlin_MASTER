package bu.ac.kr.cameraapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bu.ac.kr.cameraapp.databinding.ActivityImageListBinding

class ImageListActivity : AppCompatActivity() {
    companion object{
        private const val URI_LIST_KEY = "uriList"

        fun newIntent(activity: Activity,uriList: List<Uri>) =
            Intent(activity, ImageListActivity::class.java).apply {
                putExtra(URI_LIST_KEY , ArrayList<Uri>().apply { uriList.forEach {add(it)} })
            }
    }

    private lateinit var binding : ActivityImageListBinding
    private val uriList by lazy<List<Uri>> { intent.getParcelableArrayListExtra(URI_LIST_KEY)!!}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }
    private fun initViews() {
        setSupportActionBar(binding.toolbar)
    }
    private fun setUpImageList() =with(binding){
        uriList
    }
}