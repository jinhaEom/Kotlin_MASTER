package bu.ac.kr.bestseller_interpark.model

import com.google.gson.annotations.SerializedName

data class BestSellerDto(
    @SerializedName("title") val title:String,
    @SerializedName("item") val books:List<Book>,

    )
