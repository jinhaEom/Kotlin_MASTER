package bu.ac.kr.subway_arriveinfo.data.api

import bu.ac.kr.subway_arriveinfo.data.db.entity.mapper.StationEntity
import bu.ac.kr.subway_arriveinfo.data.db.entity.mapper.SubwayEntity

interface StationApi {
    suspend fun getStationDataUpdatedTimeMillis(): Long  //언제 최종적으로 업데이트 되었는지

    suspend fun getStationSubways() : List<Pair<StationEntity, SubwayEntity>>
}