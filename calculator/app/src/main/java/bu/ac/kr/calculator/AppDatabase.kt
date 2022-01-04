package bu.ac.kr.calculator

import androidx.room.Database
import androidx.room.RoomDatabase
import bu.ac.kr.calculator.dao.HistoryDao
import bu.ac.kr.calculator.model.History

@Database(entities = [History::class], version =1)
abstract  class AppDatabase : RoomDatabase(){
    abstract fun historyDao(): HistoryDao
}