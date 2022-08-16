package bu.ac.kr.github_repository_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isGone
import bu.ac.kr.github_repository_app.data.database.DataBaseProvider
import bu.ac.kr.github_repository_app.data.entity.GithubOwner
import bu.ac.kr.github_repository_app.data.entity.GithubRepoEntity
import bu.ac.kr.github_repository_app.databinding.ActivityMainBinding
import bu.ac.kr.github_repository_app.view.RepositoryRecyclerAdapter
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()
    private lateinit var binding : ActivityMainBinding

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val repositoryDao by lazy{ DataBaseProvider.provideDB(applicationContext).repositoryDao()}

    private lateinit var adapter: RepositoryRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initViews()


    }
    private fun initAdapter(){
        adapter = RepositoryRecyclerAdapter()

    }

    private fun initViews() = with(binding) {
        emptyResultTextView.isGone = true
        recyclerView.adapter = adapter
        searchButton.setOnClickListener {
            startActivity(
                Intent(this@MainActivity, SearchActivity::class.java)
            )
        }
    }
    override fun onResume(){
        super.onResume()
        launch(coroutineContext){
            loadLikedRepositoryList()
        }
    }
    private suspend fun loadLikedRepositoryList() = withContext(Dispatchers.IO){
        val repoList = repositoryDao.getHistory()
        withContext(Dispatchers.Main){
            setData(repoList)
        }
    }
    private fun setData(githubRepoList: List<GithubRepoEntity>) = with(binding) {
        if (githubRepoList.isEmpty()) {
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        } else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setSearchResultList(githubRepoList) {
                startActivity(
                    Intent(this@MainActivity, RepositoryActivity::class.java).apply {
                        putExtra(RepositoryActivity.REPOSITORY_OWNER_KEY, it.owner.login)
                        putExtra(RepositoryActivity.REPOSITORY_NAME_KEY, it.name)
                    }
                )
            }
        }
    }

}