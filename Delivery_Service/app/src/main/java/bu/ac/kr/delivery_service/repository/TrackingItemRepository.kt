package bu.ac.kr.delivery_service.repository

import bu.ac.kr.delivery_service.entity.TrackingInformation
import bu.ac.kr.delivery_service.entity.TrackingItem

interface TrackingItemRepository {

    suspend fun getTrackingItemInformation() : List<Pair<TrackingItem, TrackingInformation>>
}