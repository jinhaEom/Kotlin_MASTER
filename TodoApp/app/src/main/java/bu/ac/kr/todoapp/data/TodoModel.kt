package bu.ac.kr.todoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_todo")  //@Entity 의 tableName = : 테이블명 지정
data class TodoModel(

    @PrimaryKey(autoGenerate = true)
    var id: Long?,

    @ColumnInfo(name = "seq")
    var seq: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "content")
    var content: String,

    @ColumnInfo(name = "createDate")
    var createDate: Long

) {
    constructor() : this(null, 0, "", "", -1)
}