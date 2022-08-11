package bu.ac.kr.github_repository_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bu.ac.kr.github_repository_app.databinding.ActivitySearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class SearchActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var binding : ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
    private fun initAdapter() {

    }
    private fun initViews() = with(binding){

    }

    private fun bindViews() = with(binding){

    }
}