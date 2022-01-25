package bu.ac.kr.bestseller_interpark.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History(
    @PrimaryKey val uid: Int?,
    @ColumnInfo(name= "keyword") val keyword:String?
)