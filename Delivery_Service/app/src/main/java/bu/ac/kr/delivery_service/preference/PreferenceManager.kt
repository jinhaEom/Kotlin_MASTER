package bu.ac.kr.delivery_service.preference

interface PreferenceManager {

    fun getLong(key: String): Long?

    fun putLong(key: String, value: Long)
}