package bu.ac.kr.github_repository_app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import bu.ac.kr.github_repository_app.data.dao.RepositoryDao
import bu.ac.kr.github_repository_app.data.entity.GithubRepoEntity

@Database(entities = [GithubRepoEntity::class], version = 1)
abstract class SimpleGithubDatabase : RoomDatabase() {

    abstract fun repositoryDao() : RepositoryDao

}



