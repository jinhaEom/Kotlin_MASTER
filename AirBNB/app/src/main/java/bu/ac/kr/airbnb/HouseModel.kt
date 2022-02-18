package bu.ac.kr.airbnb

data class HouseModel(
    val id : Int,
    val title : String,
    val price : String,
    val imageUrl : String,
    val lat : Double,
    val lng : Double
)
