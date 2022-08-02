package bu.ac.kr.subway_arriveinfo.data.db.entity

import androidx.room.*
import bu.ac.kr.subway_arriveinfo.data.db.entity.mapper.StationEntity
import bu.ac.kr.subway_arriveinfo.data.db.entity.mapper.StationWithSubwaysEntity
import bu.ac.kr.subway_arriveinfo.data.db.entity.mapper.StationsSubwayCrossRefEntity
import bu.ac.kr.subway_arriveinfo.data.db.entity.mapper.SubwayEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDao {
    @Transaction
    @Query("SELECT * FROM StationEntity")
    fun getStationWithSubways(): Flow<List<StationWithSubwaysEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStations(station : List<StationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE) //insert할 시 primarykey가 겹치는 것이 있으면 덮어쓴다는 의미
    suspend fun insertSubways(subways : List<SubwayEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossReferences(reference: List<StationsSubwayCrossRefEntity>)

    @Transaction
    suspend fun insertStationSubways(stationSubways :List<Pair<StationEntity, SubwayEntity>>){
        insertStations(stationSubways.map{ it.first})
        insertSubways(stationSubways.map{ it.second})
        insertCrossReferences(
            stationSubways.map {( station, subway) ->
                StationsSubwayCrossRefEntity(
                    station.stationName,
                    subway.subwayId
                )
            }
        )

    }
}