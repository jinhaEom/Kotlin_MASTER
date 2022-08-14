package bu.ac.kr.github_repository_app

import android.content.Intent
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

    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: RepositoryRecyclerAdapter

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

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

    private fun initViews() = with(binding) {
        emptyResultTextView.isGone = true
        recyclerView.adapter = adapter
    }

    private fun bindViews() = with(binding) {
        searchButton.setOnClickListener {
            searchKeyword(searchBarInputView.text.toString())
        }
    }

    private fun searchKeyword(keywordString: String) {
        showLoading(true)
        launch(coroutineContext) {
            try {
                withContext(Dispatchers.IO) {
                    val response = RetrofitUtil.githubApiService.searchRepositories(
                        query = keywordString
                    )
                    if (response.isSuccessful) {
                        val body = response.body()
                        withContext(Dispatchers.Main) {
                            body?.let { searchResponse ->
                                setData(searchResponse.items)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@SearchActivity, "검색하는 과정에서 에러가 발생했습니다. : ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setData(items: List<GithubRepoEntity>){
        adapter.setSearchResultList(items){
            Toast.makeText(this, "entity : $it", Toast.LENGTH_SHORT).show()
            startActivity(
                Intent(this, RepositoryActivity::class.java).apply{
                    putExtra(RepositoryActivity.REPOSITORY_OWNER_KEY, it.owner.login)
                    putExtra(RepositoryActivity.REPOSITORY_NAME_KEY, it.name)

                }
            )
        }
    }

    private fun showLoading(isShown: Boolean) = with(binding) {
        progressBar.isGone = isShown.not()
    }

}