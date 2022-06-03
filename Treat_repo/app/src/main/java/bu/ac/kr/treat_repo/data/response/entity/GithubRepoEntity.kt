package bu.ac.kr.treat_repo.data.response.entity

import androidx.room.Embedded
import androidx.room.PrimaryKey

data class GithubRepoEntity (
    val name: String,
    @PrimaryKey val fullName: String,
    @Embedded val owner: GithubOwner,
    val description : String?,
    val language : String,
    val updatedAt : String,
    val stargazersCount: Int
        )