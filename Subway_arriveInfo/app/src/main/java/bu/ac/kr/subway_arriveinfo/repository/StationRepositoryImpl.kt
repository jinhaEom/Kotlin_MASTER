package bu.ac.kr.subway_arriveinfo.repository

import bu.ac.kr.subway_arriveinfo.data.api.StationApi
import bu.ac.kr.subway_arriveinfo.data.db.entity.StationDao
import bu.ac.kr.subway_arriveinfo.data.db.entity.mapper.StationsSubwayCrossRefEntity
import bu.ac.kr.subway_arriveinfo.data.db.entity.mapper.toStations
import bu.ac.kr.subway_arriveinfo.domain.Station
import bu.ac.kr.subway_arriveinfo.preferences.PreferenceManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class StationRepositoryImpl(
    private val stationApi: StationApi,
    private val stationDao : StationDao,
    private val preferenceManager : PreferenceManager,
    private val dispatcher : CoroutineDispatcher
    ) : StationRepository {
    override val stations: Flow<List<Station>> =
        stationDao.getStationWithSubways()
            .distinctUntilChanged()
            .map{ it.toStations()}
            .flowOn(dispatcher)

    override suspend fun refreshStations()= withContext(dispatcher){
        val fileUpdatedTimeMillis = stationApi.getStationDataUpdatedTimeMillis()
        val lastDatabaseUpdatedTimeMillis = preferenceManager.getLong(
            KEY_LAST_DATABASE_UPDATED_TIME_MILLIS)

        if(lastDatabaseUpdatedTimeMillis == null || fileUpdatedTimeMillis > lastDatabaseUpdatedTimeMillis){
            stationDao.insertStationSubways(stationApi.getStationSubways())
            preferenceManager.putLong(KEY_LAST_DATABASE_UPDATED_TIME_MILLIS,fileUpdatedTimeMillis)
        }
    }
    companion object {
        private const val KEY_LAST_DATABASE_UPDATED_TIME_MILLIS = "KEY_LAST_DATABASE_UPDATED_TIME_MILLIS"
    }
}