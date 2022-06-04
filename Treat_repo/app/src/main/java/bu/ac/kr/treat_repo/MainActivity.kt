package bu.ac.kr.treat_repo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import bu.ac.kr.treat_repo.data.response.database.DataBaseProvider
import bu.ac.kr.treat_repo.data.response.entity.GithubOwner
import bu.ac.kr.treat_repo.data.response.entity.GithubRepoEntity
import bu.ac.kr.treat_repo.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class MainActivity: AppCompatActivity(), CoroutineScope {

    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val repositoryDao by lazy{ DataBaseProvider.provideDB(applicationContext).repositoryDao()}

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launch{
            addMockData()
            val githubRepositories = loadGithubRepositories()
            withContext(coroutineContext){
                Log.e("repositories", githubRepositories.toString())
            }
        }

    }
    private suspend fun addMockData() = withContext(Dispatchers.IO){
        val mockData = (0 until  10).map{
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
        DataBaseProvider.provideDB(applicationContext).repositoryDao().insertAll(mockData)
    }
    private suspend fun loadGithubRepositories() = withContext(Dispatchers.IO){
        val repositories = repositoryDao.getHistory()
        return@withContext repositories
    }
}