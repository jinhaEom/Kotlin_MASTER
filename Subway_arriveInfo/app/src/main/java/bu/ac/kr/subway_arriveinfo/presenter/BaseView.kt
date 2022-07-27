package bu.ac.kr.subway_arriveinfo.presenter

interface BaseView<PresenterT : BasePresenter> {

    val presenter : PresenterT
}