package bu.ac.kr.delivery_service.presentation.trackinghistory

import bu.ac.kr.delivery_service.entity.TrackingInformation
import bu.ac.kr.delivery_service.entity.TrackingItem
import bu.ac.kr.delivery_service.presentation.BasePresenter
import bu.ac.kr.delivery_service.presentation.BaseView
import bu.ac.kr.delivery_service.presentation.trackingitems.TrackingItemsContract

class TrackingHistoryContract {

    interface View : BaseView<Presenter> {

        fun hideLoadingIndicator()

        fun showTrackingItemInformation(trackingItem: TrackingItem, trackingInformation: TrackingInformation)

        fun finish()
    }

    interface Presenter : BasePresenter {

        fun refresh()

        fun deleteTrackingItem()
    }
}