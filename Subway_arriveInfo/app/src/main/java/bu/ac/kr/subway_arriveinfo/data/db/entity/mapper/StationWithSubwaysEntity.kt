package bu.ac.kr.subway_arriveinfo.data.db.entity.mapper

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class StationWithSubwaysEntity (

    @Embedded val station: StationEntity,
    @Relation(
        parentColumn= "stationName",
        entityColumn = "subwayId",
        associateBy= Junction(StationWithSubwaysEntity::class)
    )
    val subways : List<SubwayEntity>

        )