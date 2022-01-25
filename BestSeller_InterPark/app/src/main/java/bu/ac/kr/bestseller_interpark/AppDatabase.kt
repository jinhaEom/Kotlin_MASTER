package bu.ac.kr.bestseller_interpark

import androidx.room.Database
import androidx.room.RoomDatabase
import bu.ac.kr.bestseller_interpark.dao.HistoryDao
import bu.ac.kr.bestseller_interpark.dao.ReviewDao
import bu.ac.kr.bestseller_interpark.model.History
import bu.ac.kr.bestseller_interpark.model.Review

@Database(entities =[History::class, Review::class],version=1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun historyDao(): HistoryDao
    abstract fun reviewDao(): ReviewDao
}