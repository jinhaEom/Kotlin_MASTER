package bu.ac.kr.delivery_service.presentation.addtrackingitem

import bu.ac.kr.delivery_service.entity.ShippingCompany
import bu.ac.kr.delivery_service.presentation.BasePresenter
import bu.ac.kr.delivery_service.presentation.BaseView

class AddTrackingItemsContract{
    interface View : BaseView<Presenter> {
        fun showShippingCompaniesLoadingIndicator()   // 택배사 불러오는 indicator 불러오고 숨기기

        fun hideShippingCompaniesLoadingIndicator()

        fun showSaveTrackingItemIndicator()  //trackingItem 저장할떄 progressBar 보여주고 숨기기

        fun hideSaveTrackingItemIndicator()

        fun showCompanies(companies : List<ShippingCompany>)  //불러온 택배사 보여주기

        fun enableSaveButton()   //운송장이 선택되었을때만 button  클릭 가능/불가능

        fun disableSaveButton()

        fun showErrorToast(message : String)  //에러발생시

        fun finish()  //화면종료
    }
    interface Presenter : BasePresenter {

        var invoice : String?
        var shippingCompanies : List<ShippingCompany>?
        var selectedShippingCompany : ShippingCompany?

        fun fetchShippingCompanies()  //택배사 목록

        fun fetchRecommendShippingCompany()

        fun changeSelectedShippingCompany(companyName: String)  //선택한 택배사 변경

        fun changeShippingInvoice(invoice : String) //운송장

        fun saveTrackingItem()  //TrackingItem 저장
    }
}