package bu.ac.kr.github_repository_app

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import bu.ac.kr.github_repository_app.data.database.DataBaseProvider
import bu.ac.kr.github_repository_app.data.entity.GithubRepoEntity
import bu.ac.kr.github_repository_app.databinding.ActivityRepositoryBinding
import bu.ac.kr.github_repository_app.extensions.loadCenterInside
import bu.ac.kr.github_repository_app.utility.RetrofitUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RepositoryActivity : AppCompatActivity(), CoroutineScope {

    val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var binding: ActivityRepositoryBinding
    private val repositoryDao by lazy {
        DataBaseProvider.provideDB(applicationContext).repositoryDao()
    }

    companion object {
        const val REPOSITORY_OWNER_KEY = "REPOSITORY_OWNER_KEY"
        const val REPOSITORY_NAME_KEY = "REPOSITORY_NAME_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val repositoryOwner = intent.getStringExtra(REPOSITORY_OWNER_KEY) ?: kotlin.run {
            toast("Repository Owner 이름이 없습니다.")
            finish()
            return
        }
        val repositoryName = intent.getStringExtra(REPOSITORY_NAME_KEY) ?: kotlin.run {
            toast("Repository  이름이 없습니다.")
            finish()
            return

        }
        launch {
            loadRepository(repositoryOwner, repositoryName)?.let {
                setData(it)
            } ?: run {
                toast("Repository 정보가 없습니다.")
                finish()
            }
        }
        showLoading(true)
    }

    private suspend fun loadRepository(
        repositoryOwner: String,
        repositoryName: String,
    ): GithubRepoEntity? =
        withContext(coroutineContext) {
            var repositoryEntity: GithubRepoEntity? = null
            withContext(Dispatchers.IO) {
                val response = RetrofitUtil.githubApiService.getRepository(
                    ownerLogin = repositoryOwner,
                    repoName = repositoryName
                )
                if (response.isSuccessful) {
                    val body = response.body()
                    withContext(Dispatchers.Main) {
                        body?.let { repo ->
                            repositoryEntity = repo
                        }
                    }
                }
            }
            repositoryEntity
        }

    private fun setData(githubRepoEntity: GithubRepoEntity) = with(binding) {
        showLoading(isShown = false)
        ownerProfileImageView.loadCenterInside(githubRepoEntity.owner.avatarUrl, 42f)
        ownerNameAndRepoNameTextView.text =
            "${githubRepoEntity.owner.login}/ ${githubRepoEntity.name}"
        stargazersCountText.text = githubRepoEntity.stargazersCount.toString()
        githubRepoEntity.language?.let { language ->
            languageText.isGone = false
            languageText.text = language
        } ?: kotlin.run {
            languageText.isGone = true
            languageText.text = ""
        }
        descriptionTextView.text = githubRepoEntity.description
        updateTimeTextView.text = githubRepoEntity.updatedAt

        setLikeState(githubRepoEntity)
    }

    private fun setLikeState(githubRepoEntity: GithubRepoEntity) = launch {
        with(Dispatchers.IO) {
            val repository = repositoryDao.getRepository(githubRepoEntity.fullName)
            val isLike = repository != null
            withContext(Dispatchers.Main) {
                setLikeImage(isLike)
                binding.likeButton.setOnClickListener {
                    likeGithubRepo(githubRepoEntity, isLike)
                }
            }
        }
    }

    private fun setLikeImage(isLike: Boolean) = with(binding) {
        binding.likeButton.setImageDrawable(ContextCompat.getDrawable(this@RepositoryActivity,
            if (isLike) {
                R.drawable.ic_like
            } else {
                R.drawable.ic_dislike

            }
        ))
    }

    private fun likeGithubRepo(githubRepoEntity: GithubRepoEntity, isLike: Boolean) = launch {
        withContext(Dispatchers.IO) {
            if (isLike) {
                repositoryDao.remove(githubRepoEntity.fullName)
            } else {
                repositoryDao.insert(githubRepoEntity)
            }
            withContext(Dispatchers.Main) {
                setLikeImage(isLike.not())
            }
        }

    }

    private fun showLoading(isShown: Boolean) = with(binding) {
        progressBar.isGone = isShown.not()
    }

    private fun Context.toast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}