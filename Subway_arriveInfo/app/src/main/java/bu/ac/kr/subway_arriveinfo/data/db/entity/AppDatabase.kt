package bu.ac.kr.subway_arriveinfo.data.db.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import bu.ac.kr.subway_arriveinfo.data.db.entity.mapper.StationEntity
import bu.ac.kr.subway_arriveinfo.data.db.entity.mapper.StationsSubwayCrossRefEntity
import bu.ac.kr.subway_arriveinfo.data.db.entity.mapper.SubwayEntity

@Database(
    entities = [StationEntity::class, SubwayEntity::class, StationsSubwayCrossRefEntity::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stationDao() : StationDao
    companion object {
        private const val DATABASE_NAME ="station.db"

        fun build(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }
}