package bu.ac.kr.sns_upload.gallery

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bu.ac.kr.sns_upload.R
import bu.ac.kr.sns_upload.databinding.ActivityGalleryBinding

class GalleryActivity : AppCompatActivity() {

    companion object{
        fun newIntent(activity: Activity) = Intent(activity, GalleryActivity::class.java)

        private const val URI_LIST_KEY = "uriList"
    }
    private lateinit var binding: ActivityGalleryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }
    private fun initViews() = with(binding){


    }
}

