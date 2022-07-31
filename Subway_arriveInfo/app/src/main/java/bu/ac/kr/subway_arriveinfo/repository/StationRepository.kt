package bu.ac.kr.subway_arriveinfo.repository

import bu.ac.kr.subway_arriveinfo.domain.Station
import kotlinx.coroutines.flow.Flow

interface StationRepository {

    val stations: Flow<List<Station>>

    suspend fun refreshStations()
}