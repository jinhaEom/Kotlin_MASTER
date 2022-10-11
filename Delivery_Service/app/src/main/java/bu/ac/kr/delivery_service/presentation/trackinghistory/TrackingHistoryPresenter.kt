package bu.ac.kr.delivery_service.presentation.trackinghistory

import bu.ac.kr.delivery_service.entity.TrackingInformation
import bu.ac.kr.delivery_service.entity.TrackingItem
import bu.ac.kr.delivery_service.repository.TrackingItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class TrackingHistoryPresenter(

    private val view : TrackingHistoryContract.View,
    private val trackerRepository : TrackingItemRepository,
    private val trackingItem : TrackingItem,
    private var trackingInformation : TrackingInformation
) : TrackingHistoryContract.Presenter{

    override val scope : CoroutineScope = MainScope()

    override fun onViewCreated() {
        view.showTrackingItemInformation(trackingItem, trackingInformation)
    }

    override fun onDestroyView() {}

    override fun refresh() {
        scope.launch{
            try{
                val newTrackingInformation =
                    trackerRepository.getTrackingItemInformation(trackingItem.company.code,trackingItem.invoice)
                newTrackingInformation?.let{
                    trackingInformation = it
                    view.showTrackingItemInformation(trackingItem, trackingInformation)
                }
            }catch(exception : Exception){
                exception.printStackTrace()
            }finally {
                view.hideLoadingIndicator()
            }
        }
    }
}