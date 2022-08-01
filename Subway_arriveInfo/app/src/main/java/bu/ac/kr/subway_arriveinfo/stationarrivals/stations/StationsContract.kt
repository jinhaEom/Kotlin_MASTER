package bu.ac.kr.subway_arriveinfo.stationarrivals.stations

import bu.ac.kr.subway_arriveinfo.domain.Station
import bu.ac.kr.subway_arriveinfo.presenter.BasePresenter
import bu.ac.kr.subway_arriveinfo.presenter.BaseView

interface StationsContract {

    interface View : BaseView<Presenter> {

        fun showLoadingIndicator()
        fun hideLoadingIndicator()

        fun showStations(stations: List<Station>)
    }
    interface Presenter : BasePresenter {
        fun filterStations(query : String
)
    }
}