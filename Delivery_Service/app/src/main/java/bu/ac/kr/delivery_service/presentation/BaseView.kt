package bu.ac.kr.delivery_service.presentation

interface BaseView<PresenterT : BasePresenter> {

    val presenter: PresenterT
}