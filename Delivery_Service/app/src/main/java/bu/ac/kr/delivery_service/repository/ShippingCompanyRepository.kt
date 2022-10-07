package bu.ac.kr.delivery_service.repository

import bu.ac.kr.delivery_service.entity.ShippingCompany


interface ShippingCompanyRepository {

    suspend fun getShippingCompanies(): List<ShippingCompany>
    suspend fun getRecommendShippingCompany(invoice: String): ShippingCompany?

}