package bu.ac.kr.delivery_service.work

import androidx.work.DelegatingWorkerFactory
import bu.ac.kr.delivery_service.repository.TrackingItemRepository
import kotlinx.coroutines.CoroutineDispatcher

class AppWorkerFactory(
    trackingItemRepository : TrackingItemRepository,
    dispatcher : CoroutineDispatcher
)  : DelegatingWorkerFactory(){
    init{
        addFactory(TrackingCheckWorkerFactory(trackingItemRepository, dispatcher))
    }
}