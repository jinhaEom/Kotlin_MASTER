package bu.ac.kr.subway_arriveinfo.preferences

interface PreferenceManager {

    fun getLong(key : String): Long?

    fun putLong(key: String, value : Long)
}