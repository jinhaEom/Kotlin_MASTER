package bu.ac.kr.subway_arriveinfo.data.db.entity.mapper

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SubwayEntity (
    @PrimaryKey val subwayId: Int,
        )