package bu.ac.kr.subway_arriveinfo.data.api

import bu.ac.kr.subway_arriveinfo.data.db.entity.mapper.StationEntity
import bu.ac.kr.subway_arriveinfo.data.db.entity.mapper.SubwayEntity
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

//firebase storeage api

class StationStorageApi(
    firebaseStorage: FirebaseStorage
) : StationApi {

    private val sheetReference = firebaseStorage.reference.child(STATION_DATA_FILE_NAME)

    override suspend fun getStationDataUpdatedTimeMillis(): Long =
        sheetReference.metadata.await().updatedTimeMillis

        override suspend fun getStationSubways(): List<Pair<StationEntity, SubwayEntity>> {
            val downloadSizeBytes = sheetReference.metadata.await().sizeBytes
            val byteArray = sheetReference.getBytes(downloadSizeBytes).await()

            return byteArray.decodeToString()
                .lines()
                .drop(1)
                .map { it.split(",")}
                .map { StationEntity(it[1]) to SubwayEntity(it[0].toInt())}
        }
        companion object {
            private const val STATION_DATA_FILE_NAME = "station_data.csv"
        }
    }
