package bu.ac.kr.github_repository_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import bu.ac.kr.github_repository_app.data.database.DataBaseProvider
import bu.ac.kr.github_repository_app.data.entity.GithubOwner
import bu.ac.kr.github_repository_app.data.entity.GithubRepoEntity
import bu.ac.kr.github_repository_app.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()
    private lateinit var binding : ActivityMainBinding

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val repositoryDao by lazy{ DataBaseProvider.provideDB(applicationContext).repositoryDao()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        launch{
            addMockData()
            val githubRepositories = loadGithubRepositories()
            withContext(coroutineContext){
                Log.e("repositories", githubRepositories.toString())
            }
        }

    }

    private fun initViews() = with(binding){
        searchButton.setOnClickListener{
            startActivity(
                Intent(this@MainActivity,SearchActivity::class.java)
            )
        }

    }
    private suspend fun addMockData() = withContext(Dispatchers.IO) {
        val mockData = (0 until 9).map {
            GithubRepoEntity(
                name = "repo $it",
                fullName = "name $it",
                owner = GithubOwner(
                    "login",
                    "avatarUrl"
                ),
                description = null,
                language = null,
                updatedAt = Date().toString(),
                stargazersCount = it
            )
        }
        repositoryDao.insertAll(mockData)
    }
    private suspend fun loadGithubRepositories() = withContext(Dispatchers.IO){
        val repositories = repositoryDao.getHistory()
        return@withContext repositories
    }


}