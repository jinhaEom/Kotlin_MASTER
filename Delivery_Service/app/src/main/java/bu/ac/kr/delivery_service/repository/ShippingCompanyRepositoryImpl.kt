package bu.ac.kr.delivery_service.repository

import android.preference.PreferenceManager
import bu.ac.kr.delivery_service.api.SweetTrackerApi
import bu.ac.kr.delivery_service.db.ShippingCompanyDao
import bu.ac.kr.delivery_service.entity.ShippingCompany
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ShippingCompanyRepositoryImpl(
    private val trackerApi : SweetTrackerApi,
    private val shippingCompanyDao : ShippingCompanyDao,
    private val preferenceManager : PreferenceManager,
    private val dispatcher : CoroutineDispatcher
    ) : ShippingCompanyRepository {
        override suspend fun getShipppingCompanies() : List<ShippingCompany> = withContext(dispatcher){
            val currentTimeMillis = System.currentTimeMillis()
            val lastDatabaseUpdatedTimeMillis = preferenceManager.getLong(KEY_LAST_DATABASE_UPDATED_TIME_MILLIS)

            if(lastDatabaseUpdatedTimeMillis == null ||
                CACHE_MAX_AGE_MILLIS < (currentTimeMillis - lastDatabaseUpdatedTimeMillis)
            ){
                val shippingCompanies = trackerApi.getShippingCompanies()
                    .body()
                    ?.shippingCompanies
                    ?: emptyList()
                shippingCompanyDao.insert(shippingCompanies)
                preferenceManager.putLong(KEY_LAST_DATABASE_UPDATED_TIME_MILLIS, currentTimeMillis)
            }
            shippingCompanyDao.getAll()
        }
    companion object{
        private const val KEY_LAST_DATABASE_UPDATED_TIME_MILLIS = "KEY_LAST_DATABASE_UPDATED_TIME_MILLIS"
        private const val CACHE_MAX_AGE_MILLIS = 1000L * 60 * 60 * 24 * 7
    }
}