package bu.ac.kr.github_repository_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isGone
import bu.ac.kr.github_repository_app.data.entity.GithubRepoEntity
import bu.ac.kr.github_repository_app.databinding.ActivitySearchBinding
import bu.ac.kr.github_repository_app.utility.GithubApiService
import bu.ac.kr.github_repository_app.utility.RetrofitUtil
import bu.ac.kr.github_repository_app.view.RepositoryRecyclerAdapter
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SearchActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var binding : ActivitySearchBinding
    private lateinit var adapter : RepositoryRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initViews()
        bindViews()

    }
    private fun initAdapter() {
        adapter = RepositoryRecyclerAdapter()

    }
    private fun initViews() = with(binding){
        emptyResultTextView.isGone = true
        recyclerView.adapter = adapter

    }

    private fun bindViews() = with(binding){
        searchButton.setOnClickListener{
            searchKeyword(searchBarInputView.text.toString())
        }

    }
    private fun searchKeyword(keywordString : String) = launch{
        withContext(Dispatchers.IO) {
           val response = RetrofitUtil.githubApiService.searchRepositories(keywordString)
            if(response.isSuccessful){
                val body = response.body()
                withContext(Dispatchers.Main){
                    Log.e("response", body.toString())
                    setData(it.items)
                }
            }
        }
    }
    private fun setData(items: List<GithubRepoEntity>){
        adapter.setRepositoryList(items){
            Toast.makeText(this, "entity: $it", Toast.LENGTH_SHORT).show()
        }
    }
}