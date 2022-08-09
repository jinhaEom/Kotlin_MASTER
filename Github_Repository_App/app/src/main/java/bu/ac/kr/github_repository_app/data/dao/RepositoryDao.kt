package bu.ac.kr.github_repository_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import bu.ac.kr.github_repository_app.data.entity.GithubRepoEntity

@Dao
interface RepositoryDao {

    @Insert
    suspend fun insert(repo : GithubRepoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repoList: List<GithubRepoEntity>)

    @Query("SELECT * FROM GithubRepository")
    suspend fun getHistory() : List<GithubRepoEntity>

    @Query("DELETE FROM githubRepository")
    suspend fun clearAll()
}