package bu.ac.kr.delivery_service.presentation.trackingitems

import bu.ac.kr.delivery_service.entity.TrackingInformation
import bu.ac.kr.delivery_service.entity.TrackingItem
import bu.ac.kr.delivery_service.presentation.BasePresenter
import bu.ac.kr.delivery_service.presentation.BaseView

class TrackingItemsContract {

    interface View : BaseView<Presenter> {

        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showNoDataDescription()

        fun showTrackingItemInformation(trackingItemInformation: List<Pair<TrackingItem, TrackingInformation>>)
    }

    interface Presenter : BasePresenter {

        var trackingItemInformation: List<Pair<TrackingItem, TrackingInformation>>

        fun refresh()
    }
}