package bu.ac.kr.treat_repo.data.response.database

import androidx.room.Database
import androidx.room.RoomDatabase
import bu.ac.kr.treat_repo.data.response.dao.repositoryDao

import bu.ac.kr.treat_repo.data.response.entity.GithubRepoEntity

@Database(entities = [GithubRepoEntity::class], version = 1)
abstract class SimpleGithubDatabase : RoomDatabase() {

    abstract fun repositoryDao(): repositoryDao

}