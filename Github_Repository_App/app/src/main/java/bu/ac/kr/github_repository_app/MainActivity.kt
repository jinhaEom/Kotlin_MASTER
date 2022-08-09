package bu.ac.kr.github_repository_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bu.ac.kr.github_repository_app.data.entity.GithubOwner
import bu.ac.kr.github_repository_app.data.entity.GithubRepoEntity
import bu.ac.kr.github_repository_app.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    val job = Job()
    private lateinit var binding : ActivityMainBinding

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launch{
            val dataJob = addMockData()
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
    }


}