package bu.ac.kr.subway_arriveinfo.data.db.entity.mapper

import androidx.room.Entity
import androidx.room.PrimaryKey

// 역 이름과 역 즐겨찾기 여부 entity
@Entity
data class StationEntity(
    @PrimaryKey val stationName: String,

    val isFavorited: Boolean = false,
)