package bu.ac.kr.subway_arriveinfo.data.db.entity.mapper

import androidx.room.Entity

//다대다 관계정의 ( 여러호선이 겹치는 역이 있기 때문)
@Entity(primaryKeys = ["stationName", "subwayId"])
data class StationsSubwayCrossRefEntity(
    val stationName: String,
    val subwayId: Int
)