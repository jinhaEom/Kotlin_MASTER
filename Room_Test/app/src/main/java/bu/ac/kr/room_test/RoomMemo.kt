package bu.ac.kr.room_test

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
*Room 라이브러리는 @Entity 어노테이션이 적용된 클래스를 찾아 테이블로 변환
* 테이블 명을 클래스와 다르게 하고싶으면  @Entity(tableName="테이블명") 과 같이 작성
* @Ignore 어노테이션을 적용하면 해당 변수가 테이블과 관계 없는 변수라는 정보를 알릴 수 있음.
 */
@Entity
class RoomMemo {

    //멤버 변수 nom content, date 선언

    @PrimaryKey(autoGenerate = true) // Primary key 임을 명시하고 자동 증가 옵션 추가
    @ColumnInfo
    var no: Long? = null

    @ColumnInfo
    var content: String = ""

    @ColumnInfo(name="date")
    var datetime : Long = 0

   constructor(content: String, datetime:Long) {
       this.content = content
       this.datetime = datetime
   }
}