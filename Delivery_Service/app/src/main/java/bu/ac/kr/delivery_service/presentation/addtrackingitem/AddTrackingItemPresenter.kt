package bu.ac.kr.delivery_service.presentation.addtrackingitem

import bu.ac.kr.delivery_service.entity.ShippingCompany
import bu.ac.kr.delivery_service.entity.TrackingItem
import bu.ac.kr.delivery_service.repository.ShippingCompanyRepository
import bu.ac.kr.delivery_service.repository.TrackingItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class AddTrackingItemPresenter(
    private val view : AddTrackingItemsContract.View,
    private val shippingCompanyRepository : ShippingCompanyRepository,
    private val trackerRepository : TrackingItemRepository
) : AddTrackingItemsContract.Presenter{

    override val scope : CoroutineScope = MainScope()

    override var invoice : String? = null
    override var shippingCompanies : List<ShippingCompany>? = null
    override var selectedShippingCompany : ShippingCompany? = null

    override fun onViewCreated() {  //화면이 생성되자마자 택배사 목록 가져옴
        fetchShippingCompanies()
    }

    override fun onDestroyView() {}

    override fun fetchShippingCompanies() {
        scope.launch{
            view.showShippingCompaniesLoadingIndicator()
            if(shippingCompanies.isNullOrEmpty()){
                shippingCompanies = shippingCompanyRepository.getShippingCompanies()
            }

            shippingCompanies?.let{ view.showCompanies(it)}

            view.hideShippingCompaniesLoadingIndicator()
        }
    }

    override fun changeSelectedShippingCompany(companyName : String){ //회사선택시
        selectedShippingCompany = shippingCompanies?.find { it.name == companyName }
        enableSaveButtonIfAvailable()
    }
    override fun changeShippingInvoice(invoice : String){
        this.invoice = invoice
        enableSaveButtonIfAvailable()
    }
    override fun saveTrackingItem() {
        scope.launch {
            try{
                view.showSaveTrackingItemIndicator()
                trackerRepository.saveTrackingItem(
                    TrackingItem(
                        invoice!!,
                        selectedShippingCompany!!
                    )
                )
                view.finish()
            }catch (exception : Exception){
                view.showErrorToast(exception.message ?: "서비스에 문제가 생겨 운송장을 추가하지 못했어요.")
            }finally {
                view.hideSaveTrackingItemIndicator()
            }
        }
    }
    private fun enableSaveButtonIfAvailable(){
        if(!invoice.isNullOrBlank() && selectedShippingCompany != null){
            view.enableSaveButton()
        }else {
            view.disableSaveButton()
        }
    }
}