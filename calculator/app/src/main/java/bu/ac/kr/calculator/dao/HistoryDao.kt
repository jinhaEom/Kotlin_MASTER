package bu.ac.kr.calculator.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import bu.ac.kr.calculator.model.History

@Dao  // room에 연결된 dao
interface HistoryDao{
    @Query("SELECT * FROM history") // 쿼리문 작성.  모든 히스토리를 조회하는 메서드
    fun getAll(): List<History>

    @Insert     //하나하나 저장(insert)하는 메서드 아래 작성
    fun insertHistory(history: History)


    @Query("DELETE FROM history")  //삭제하는 메서드 아래 작성.
    fun deleteAll()


}